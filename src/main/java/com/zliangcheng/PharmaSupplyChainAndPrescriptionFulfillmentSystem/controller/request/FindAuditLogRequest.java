package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAuditLogRequest {
    private String patientId;
    private String pharmacyId;
    private ResponseStatus status;
}
