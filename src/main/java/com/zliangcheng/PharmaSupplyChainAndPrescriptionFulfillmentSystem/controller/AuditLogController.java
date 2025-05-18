package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application.AuditLogApplicationService;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.AuditLog;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auditLogs")
@RestController
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogApplicationService auditLogApplicationService;

    @GetMapping("")
    public List<AuditLog> findAllAuditLog(@RequestParam(required = false) Long patientId,
                                          @RequestParam(required = false) Long pharmacyId,
                                          @RequestParam(required = false) ResponseStatus status) {
        return auditLogApplicationService.findAllAuditLog(patientId, pharmacyId, status);
    }
}
