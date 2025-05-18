package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PrescriptionResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.*;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.DrugRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PharmacyRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrescriptionApplicationServiceTest {
    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private DrugRepository drugRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PrescriptionApplicationService applicationService;

    @Test
    void givenNotFoundDrugWhenCreateThenThrowError() {
        // given
        when(drugRepository.findById(1L)).thenReturn(Optional.empty());
        CreatePrescriptionRequest request = new CreatePrescriptionRequest("1", "1", Map.of(1L, 40));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assertEquals(ResponseStatus.FAILED, response.getStatus());
        assertEquals("Drug not found with ID: 1", response.getErrorMessage());
        assertEquals("4", response.getPrescriptionId());
    }

    @Test
    void givenNotFoundPharmacyWhenCreateThenThrowError() {
        // given
        Long drugId = 1L;
        when(drugRepository.findById(1L)).thenReturn(Optional.of(createDrug(drugId)));
        when(pharmacyRepository.findById(2L)).thenReturn(Optional.empty());

        CreatePrescriptionRequest request = new CreatePrescriptionRequest("2", "1", Map.of(1L, 40));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assertEquals(ResponseStatus.FAILED, response.getStatus());
        assertEquals("Pharmacy not found with ID: 2", response.getErrorMessage());
        assertEquals("4", response.getPrescriptionId());
    }

    @Test
    void givenLargeQuantityWhenCreateThenThrowError() {
        // given
        Long drugId = 1L;
        when(drugRepository.findById(1L)).thenReturn(Optional.of(createDrug(drugId)));

        CreatePrescriptionRequest request = new CreatePrescriptionRequest("2", "1", Map.of(1L, 100));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assertEquals(ResponseStatus.FAILED, response.getStatus());
        assertEquals("药品库存不足 [药品ID: 1]", response.getErrorMessage());
        assertEquals("4", response.getPrescriptionId());
    }

    @Test
    void givenNormalQuantityWhenCreateThenSuccess() {
        // given
        Long drugId = 1L;
        Long pharmacyId = 2L;
        when(drugRepository.findById(1L)).thenReturn(Optional.of(createDrug(drugId)));
        when(pharmacyRepository.findById(2L)).thenReturn(Optional.of(createPharmacy(pharmacyId, drugId)));

        CreatePrescriptionRequest request = new CreatePrescriptionRequest("2", "1", Map.of(1L, 10));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assertEquals(ResponseStatus.SUCCESS, response.getStatus());
        assertTrue(response.getErrorMessage().isEmpty());
        assertEquals("4", response.getPrescriptionId());
    }

    @Test
    void givenNormalPrescriptionIdWhenFulfillThenSuccess() {
        // given
        Long prescriptionId = 4L;
        Long pharmacyId = 2L;
        Long drugId = 1L;

        when(drugRepository.findById(drugId)).thenReturn(Optional.of(createDrug(drugId)));
        when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.of(createPharmacy(pharmacyId, drugId)));
        when(prescriptionRepository.findById(prescriptionId))
                .thenReturn(Optional.of(createPrescription(prescriptionId, pharmacyId, drugId)));

        // when
        PrescriptionResponse response = applicationService.fulfill(String.valueOf(prescriptionId));
        // then
        assertEquals("", response.getErrorMessage());
        assertEquals(ResponseStatus.SUCCESS, response.getStatus());
    }

    @Test
    void givenNotFoundPrescriptionIdWhenFulfillThenSuccess() {
        // given
        when(prescriptionRepository.findById(4L)).thenReturn(Optional.empty());
        // when
        PrescriptionResponse response = applicationService.fulfill("4");
        // then
        assertEquals("4", response.getPrescriptionId());
        assertEquals("Prescription not found with ID: 4", response.getErrorMessage());
        assertEquals(ResponseStatus.FAILED, response.getStatus());
    }

    private static Drug createDrug(Long id) {
        Drug drug = new Drug();
        drug.setId(id);
        drug.setStock(50);
        LocalDate expiryDate = LocalDate.MAX;
        drug.setExpiryDate(expiryDate);
        return drug;
    }

    private static Pharmacy createPharmacy(Long id, Long drugId) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(id);
        Map<Long, Integer> drugAllocations = new HashMap<>();
        drugAllocations.put(drugId, 40);
        pharmacy.setDrugAllocations(drugAllocations);
        return pharmacy;
    }

    private static Prescription createPrescription(Long prescriptionId, Long pharmacyId, Long drugId) {
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionId);
        prescription.setStatus(PrescriptionStatus.PENDING);
        prescription.setPharmacyId(pharmacyId);
        prescription.setDrugs(Map.of(drugId, 10));
        return prescription;
    }

}