package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionApplicationService {
    private final PrescriptionRepository prescriptionRepository;

    public void create(CreatePrescriptionRequest request) {
        Prescription prescription = new Prescription(request.getPharmacyId(), request.getDrugs());
        prescriptionRepository.save(prescription);
    }

    public void fulfill(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(Long.valueOf(prescriptionId)).get();
        prescription.fulfill();
        prescriptionRepository.save(prescription);
    }
}
