package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception;

import lombok.Getter;

@Getter
public class DrugInsufficientStockException extends DrugException {
  private final Long drugId;

  public DrugInsufficientStockException(Long drugId) {
    super(String.format("药品库存不足 [药品ID: %d]", drugId));
    this.drugId = drugId;
  }

}
