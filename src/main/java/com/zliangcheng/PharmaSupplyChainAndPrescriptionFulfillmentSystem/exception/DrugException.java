package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception;

public class DrugException extends RuntimeException {
    public DrugException(String message) {
        super(message);
    }

    public DrugException(String message, Throwable cause) {
        super(message, cause);
    }
}
