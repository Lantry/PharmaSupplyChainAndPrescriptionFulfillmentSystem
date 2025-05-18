package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.PrescriptionInvalidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrescriptionTest {

    @Test
    void validateOrderForFulfillmentForSuccess() {
        // given
        Prescription prescription = new Prescription();
        prescription.setStatus(PrescriptionStatus.PENDING);
        // when
        prescription.validateOrderForFulfillment();
        // then
    }

    @Test
    void validateOrderForFulfillmentForFailed() {
        // given
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setStatus(PrescriptionStatus.PROCESSED);
        // when
        PrescriptionInvalidException invalidException = assertThrows(PrescriptionInvalidException.class, prescription::validateOrderForFulfillment);
        // then
        assertEquals("订单无效 [订单ID: 1, 订单状态: 已处理]", invalidException.getMessage());
    }

    @Test
    void fulfill() {
        // given
        Prescription prescription = new Prescription();
        prescription.setStatus(PrescriptionStatus.PENDING);
        // when
        prescription.fulfill();
        // then
        assertEquals(PrescriptionStatus.PROCESSED, prescription.getStatus());
    }

    @Test
    void invalid() {
        // given
        Prescription prescription = new Prescription();
        prescription.setStatus(PrescriptionStatus.PENDING);
        // when
        prescription.invalid();
        // then
        assertEquals(PrescriptionStatus.REJECTED, prescription.getStatus());
    }
}