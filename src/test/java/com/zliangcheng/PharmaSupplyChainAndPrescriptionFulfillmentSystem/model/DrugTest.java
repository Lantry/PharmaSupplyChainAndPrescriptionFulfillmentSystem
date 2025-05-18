package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
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
}