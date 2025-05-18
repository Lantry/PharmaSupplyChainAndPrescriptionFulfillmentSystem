package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PharmacyInfoResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.DrugRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DrugAndPharmacyApplicationService {
    private final DrugRepository drugRepository;
    private final PharmacyRepository pharmacyRepository;

    public void addDrugs(AddDrugRequest request) {
        Drug drug = Drug.from(request);
        drugRepository.save(drug);
    }

    public List<PharmacyInfoResponse> listPharmacies() {
        List<Pharmacy> pharmacies = pharmacyRepository.findAll();
        return pharmacies.stream().map(pharmacy -> {
            List<Drug> drugs = drugRepository.findAllById(pharmacy.getDrugAllocations().keySet());
            return PharmacyInfoResponse.from(pharmacy, drugs);
        }).collect(toList());
    }
}
