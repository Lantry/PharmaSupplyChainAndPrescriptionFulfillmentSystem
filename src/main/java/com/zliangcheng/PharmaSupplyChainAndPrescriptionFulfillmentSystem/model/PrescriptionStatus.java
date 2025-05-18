package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrescriptionStatus {
    PENDING("待处理"),
    PROCESSED("已处理"),
    REJECTED("已拒绝");

    private final String description;
}
