package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DrugExpiryException extends DrugException {
    private final Long drugId;
    private final LocalDate expiryDate;

    public DrugExpiryException(Long drugId, LocalDate expiryDate) {
        super(String.format("药品已过期 [药品ID: %d, 过期时间: %s]", drugId, expiryDate));
        this.drugId = drugId;
        this.expiryDate = expiryDate;
    }
}
