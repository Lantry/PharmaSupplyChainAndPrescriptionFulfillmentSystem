package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugExpiryException;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugInsufficientStockException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
class DrugTest {

    @Test
    void testFrom() {
        // given
        LocalDate now = LocalDate.now();
        AddDrugRequest request = new AddDrugRequest("testName", "manufacturer", "N110", now, 50);
        // when
        Drug drug = Drug.from(request);
        // then
        assert "testName".equals(drug.getName());
        assert "manufacturer".equals(drug.getManufacturer());
        assert "N110".equals(drug.getBatchNumber());
        assert now.equals(drug.getExpiryDate());
        assert drug.getStock().equals(0);
    }

    @Test
    void validateDrugThrowExpired() {
        // given
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setStock(50);
        LocalDate expiryDate = LocalDate.of(2024, 1, 1);
        drug.setExpiryDate(expiryDate);
        // when
        DrugExpiryException expiryException = assertThrows(DrugExpiryException.class, () -> drug.validateDrug(10));
        // then
        assert expiryException.getMessage().equals(String.format("药品已过期 [药品ID: %d, 过期时间: %s]", 1, expiryDate));
    }

    @Test
    void validateDrugThrowInsufficient() {
        // given
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setStock(50);
        LocalDate expiryDate = LocalDate.MAX;
        drug.setExpiryDate(expiryDate);
        // when
        DrugInsufficientStockException insufficientStockException = assertThrows(DrugInsufficientStockException.class, () -> drug.validateDrug(100));
        // then
        assert insufficientStockException.getMessage().equals(String.format("药品库存不足 [药品ID: %d]", 1));
    }
}