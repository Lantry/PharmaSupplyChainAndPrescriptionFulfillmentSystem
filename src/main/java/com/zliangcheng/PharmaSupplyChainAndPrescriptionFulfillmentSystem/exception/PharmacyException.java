package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception;

import lombok.Getter;

@Getter
public class PharmacyException extends RuntimeException {
    private final Long drugId;
    private final Long pharmacyId;

    public PharmacyException(Long pharmacyId, Long drugId) {
        super(String.format("药房无法分配药品 [药房ID: %d, 药品ID: %d]", pharmacyId, drugId));
        this.drugId = drugId;
        this.pharmacyId = pharmacyId;
    }
}
