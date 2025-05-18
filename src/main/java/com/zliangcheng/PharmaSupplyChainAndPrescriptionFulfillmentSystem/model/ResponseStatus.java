package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS("成功"),
    FAILED("失败");

    private final String description;
}
