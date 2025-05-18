package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception;

import lombok.Getter;

@Getter
public class PrescriptionInvalidException extends PrescriptionException {
    private final Long prescriptionId;
    private final String prescriptionStatus;

    public PrescriptionInvalidException(Long prescriptionId, String prescriptionStatus) {
        super(String.format("订单无效 [订单ID: %d, 订单状态: %s]", prescriptionId, prescriptionStatus));
        this.prescriptionId = prescriptionId;
        this.prescriptionStatus = prescriptionStatus;
    }
}
