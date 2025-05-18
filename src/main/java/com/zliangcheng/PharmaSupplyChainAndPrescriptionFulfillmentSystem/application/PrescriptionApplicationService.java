package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.aspect.AuditLogEnable;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PrescriptionResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.DrugRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PharmacyRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PrescriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionApplicationService {
    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;
    private final PharmacyRepository pharmacyRepository;

    @AuditLogEnable
    public PrescriptionResponse create(CreatePrescriptionRequest request) {
        Prescription prescription = new Prescription(request.getPharmacyId(), request.getDrugs(), request.getPatientId());
        String errorMessage = "";
        ResponseStatus status = ResponseStatus.SUCCESS;

        try {
            prescription.getDrugs().forEach((drugId, quantity) -> {
                Drug drug = drugRepository.findById(drugId)
                        .orElseThrow(() -> new EntityNotFoundException("Drug not found with ID: " + drugId));
                drug.validateDrug(quantity);

                Pharmacy pharmacy = pharmacyRepository.findById(prescription.getPharmacyId())
                        .orElseThrow(() -> new EntityNotFoundException("Pharmacy not found with ID: " + prescription.getPharmacyId()));
                pharmacy.validateDrugQuantities(drug, quantity);
            });
        } catch (Exception exception) {
            log.error("e: ", exception);
            prescription.invalid();
            status = ResponseStatus.FAILED;
            errorMessage = exception.getMessage();
        }

        prescriptionRepository.save(prescription);
        return PrescriptionResponse.from(prescription, errorMessage, status);
    }

    @AuditLogEnable
    public PrescriptionResponse fulfill(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(Long.valueOf(prescriptionId)).orElse(null);
        if (Objects.isNull(prescription)) {
            return PrescriptionResponse.emptyPrescription(prescriptionId);
        }

        String errorMessage = "";
        ResponseStatus status = ResponseStatus.SUCCESS;

        try {
            prescription.validateOrderForFulfillment();

            prescription.getDrugs().forEach((drugId, quantity) -> {
                Drug drug = drugRepository.findById(drugId)
                        .orElseThrow(() -> new EntityNotFoundException("Drug not found with ID: " + drugId));
                drug.deductStock(quantity);
                drugRepository.save(drug);

                Pharmacy pharmacy = pharmacyRepository.findById(prescription.getPharmacyId())
                        .orElseThrow(() -> new EntityNotFoundException("Pharmacy not found with ID: " + prescription.getPharmacyId()));
                pharmacy.deductAllocation(drug, quantity);
                pharmacyRepository.save(pharmacy);
            });

            prescription.fulfill();
            prescriptionRepository.save(prescription);
        } catch (Exception exception) {
            log.error("e: ", exception);
            errorMessage = exception.getMessage();
            status = ResponseStatus.FAILED;
            prescription.invalid();
        }

        prescriptionRepository.save(prescription);
        return PrescriptionResponse.from(prescription, errorMessage, status);
    }
}
