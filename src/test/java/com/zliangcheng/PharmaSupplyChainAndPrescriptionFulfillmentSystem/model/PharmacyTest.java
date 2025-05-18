package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.PharmacyException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PharmacyTest {

    @Test
    void validateDrugQuantitiesByQuantity() {
        // given
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setStock(50);
        LocalDate expiryDate = LocalDate.MAX;
        drug.setExpiryDate(expiryDate);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(2L);
        pharmacy.setDrugAllocations(Map.of(1L, 10));

        // when
        PharmacyException pharmacyException = assertThrows(PharmacyException.class, () -> pharmacy.validateDrugQuantities(drug, 30));

        // then
        assertEquals("药房无法分配药品 [药房ID: 2, 药品ID: 1]", pharmacyException.getMessage());
    }

    @Test
    void validateDrugQuantitiesByDrug() {
        // given
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setStock(50);
        LocalDate expiryDate = LocalDate.MAX;
        drug.setExpiryDate(expiryDate);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(2L);
        pharmacy.setDrugAllocations(Map.of(5L, 50));

        // when
        PharmacyException pharmacyException = assertThrows(PharmacyException.class, () -> pharmacy.validateDrugQuantities(drug, 30));

        // then
        assertEquals("药房无法分配药品 [药房ID: 2, 药品ID: 1]", pharmacyException.getMessage());
    }
}