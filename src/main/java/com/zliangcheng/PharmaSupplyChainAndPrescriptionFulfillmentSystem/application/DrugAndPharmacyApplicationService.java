package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.AddDrugsResponse;
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

    public AddDrugsResponse addDrugs(AddDrugRequest request) {
        String drugName = request.getName();
        Drug drug = drugRepository.findByName(drugName).orElse(Drug.from(request));
        drug.addStock(request.getStock());
        Drug save = drugRepository.save(drug);
        return new AddDrugsResponse(save.getName(), save.getStock());
    }

    public List<PharmacyInfoResponse> listPharmacies() {
        List<Pharmacy> pharmacies = pharmacyRepository.findAll();
        return pharmacies.stream().map(pharmacy -> {
            List<Drug> drugs = drugRepository.findAllById(pharmacy.getDrugAllocations().keySet());
            return PharmacyInfoResponse.from(pharmacy, drugs);
        }).collect(toList());
    }
}
