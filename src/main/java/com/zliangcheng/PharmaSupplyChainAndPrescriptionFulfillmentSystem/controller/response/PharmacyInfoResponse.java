package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyInfoResponse {
    private String pharmacyName;
    private String pharmacyAdd;
    private Map<String, Integer> drugsInfo;

    public static PharmacyInfoResponse from(Pharmacy pharmacy, List<Drug> drugs) {
        Map<String, Integer> drugsInfo = new HashMap<>();
        drugs.forEach(drug -> {
            drugsInfo.put(drug.getName(), pharmacy.getDrugAllocations().get(drug.getId()));
        });

        return new PharmacyInfoResponse(pharmacy.getName(),
                pharmacy.getAddress(),
                drugsInfo);
    }
}
