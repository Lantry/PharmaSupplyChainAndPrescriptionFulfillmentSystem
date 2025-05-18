package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application.DrugAndPharmacyApplicationService;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PharmacyInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {
    private final DrugAndPharmacyApplicationService applicationService;
    @GetMapping("/")
    public List<PharmacyInfoResponse> listPharmacyInfos() {
        return applicationService.listPharmacies();
    }
}
