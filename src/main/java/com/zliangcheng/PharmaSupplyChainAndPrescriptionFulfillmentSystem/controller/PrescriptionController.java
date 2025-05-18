package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application.PrescriptionApplicationService;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionApplicationService applicationService;

    @PostMapping("/")
    public void createPrescription(@RequestBody CreatePrescriptionRequest request) {
        applicationService.create(request);
    }

    @PostMapping("/{id}/fulfill")
    public void fulfillPrescription(@PathVariable(name = "id") String id) {
        applicationService.fulfill(id);
    }
}
