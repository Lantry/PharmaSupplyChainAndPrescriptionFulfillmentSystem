package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application.PrescriptionApplicationService;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PrescriptionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionApplicationService applicationService;

    @PostMapping("/")
    public PrescriptionResponse createPrescription(@RequestBody @Valid CreatePrescriptionRequest request) {
        return applicationService.create(request);
    }

    @PostMapping("/{id}/fulfillment")
    public PrescriptionResponse fulfillPrescription(@PathVariable(name = "id") String id) {
        return applicationService.fulfill(id);
    }
}
