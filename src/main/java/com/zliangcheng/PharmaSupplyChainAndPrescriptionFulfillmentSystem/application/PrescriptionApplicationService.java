package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PrescriptionResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.DrugRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PharmacyRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PrescriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrescriptionApplicationService {
    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public PrescriptionResponse create(CreatePrescriptionRequest request) {
        Prescription prescription = new Prescription(request.getPharmacyId(), request.getDrugs());
        String errorMessage = "";

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
            prescription.invalid();
            errorMessage = exception.getMessage();
        }

        prescriptionRepository.save(prescription);
        return PrescriptionResponse.from(prescription, errorMessage);
    }

    public void fulfill(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(Long.valueOf(prescriptionId)).get();
        prescription.fulfill();
        prescriptionRepository.save(prescription);
    }
}
