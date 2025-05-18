package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application.DrugAndPharmacyApplicationService;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drugs")
@RequiredArgsConstructor
public class DrugController {
    private final DrugAndPharmacyApplicationService applicationService;

    @PostMapping("/")
    public void addDrugs(@RequestBody AddDrugRequest request) {
        applicationService.addDrugs(request);
    }
}
