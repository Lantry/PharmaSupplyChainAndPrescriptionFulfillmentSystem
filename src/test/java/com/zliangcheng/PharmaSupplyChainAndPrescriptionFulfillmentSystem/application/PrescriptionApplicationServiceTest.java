package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.CreatePrescriptionRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PrescriptionResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.DrugRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PharmacyRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

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
        CreatePrescriptionRequest request = new CreatePrescriptionRequest("1", Map.of(1L, 40));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assert response.getStatus().equals(ResponseStatus.FAILED);
        assert response.getErrorMessage().equals("Drug not found with ID: 1");
        assert response.getPrescriptionId().equals("4");
    }

    @Test
    void givenNotFoundPharmacyWhenCreateThenThrowError() {
        // given
        when(drugRepository.findById(1L)).thenReturn(Optional.of(createDrug()));
        when(pharmacyRepository.findById(2L)).thenReturn(Optional.empty());

        CreatePrescriptionRequest request = new CreatePrescriptionRequest("2", Map.of(1L, 40));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assert response.getStatus().equals(ResponseStatus.FAILED);
        assert response.getErrorMessage().equals("Pharmacy not found with ID: 2");
        assert response.getPrescriptionId().equals("4");
    }

    @Test
    void givenLargeQuantityWhenCreateThenThrowError() {
        // given

        when(drugRepository.findById(1L)).thenReturn(Optional.of(createDrug()));

        CreatePrescriptionRequest request = new CreatePrescriptionRequest("2", Map.of(1L, 100));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assert response.getStatus().equals(ResponseStatus.FAILED);
        assert response.getErrorMessage().equals("药品库存不足 [药品ID: 1]");
        assert response.getPrescriptionId().equals("4");
    }

    @Test
    void givenLargeQuantityWhenCreateThenSuccess() {
        // given
        when(drugRepository.findById(1L)).thenReturn(Optional.of(createDrug()));
        when(pharmacyRepository.findById(2L)).thenReturn(Optional.of(createPharmacy()));

        CreatePrescriptionRequest request = new CreatePrescriptionRequest("2", Map.of(1L, 10));
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription save = invocation.getArgument(0);
            save.setId(4L);
            return save;
        });
        // when
        PrescriptionResponse response = applicationService.create(request);
        // then
        assert response.getStatus().equals(ResponseStatus.SUCCESS);
        assert response.getErrorMessage().isEmpty();
        assert response.getPrescriptionId().equals("4");
    }

    private static Drug createDrug() {
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setStock(50);
        LocalDate expiryDate = LocalDate.MAX;
        drug.setExpiryDate(expiryDate);
        return drug;
    }

    private static Pharmacy createPharmacy() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setDrugAllocations(Map.of(1L, 40));
        return pharmacy;
    }
}