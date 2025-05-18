package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugExpiryException;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugInsufficientStockException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals("testName", drug.getName());
        assertEquals("manufacturer", drug.getManufacturer());
        assertEquals("N110", drug.getBatchNumber());
        assertEquals(now, drug.getExpiryDate());
        assertEquals(0, drug.getStock());
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
        assertEquals(expiryException.getMessage(), String.format("药品已过期 [药品ID: %d, 过期时间: %s]", 1, expiryDate));
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
        assertEquals(insufficientStockException.getMessage(), String.format("药品库存不足 [药品ID: %d]", 1));
    }
}