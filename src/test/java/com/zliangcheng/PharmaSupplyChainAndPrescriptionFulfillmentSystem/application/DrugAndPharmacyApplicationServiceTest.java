package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.AddDrugsResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response.PharmacyInfoResponse;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.DrugRepository;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.PharmacyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrugAndPharmacyApplicationServiceTest {
    @Mock
    private DrugRepository drugRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @InjectMocks
    private DrugAndPharmacyApplicationService drugAndPharmacyApplicationService;


    @Test
    void addNewDrugs() {
        // given
        when(drugRepository.findByName("testName")).thenReturn(Optional.empty());
        AddDrugRequest request = new AddDrugRequest("testName", "manufacturer", "N110", LocalDate.now(), 50);
        when(drugRepository.save(any(Drug.class))).thenAnswer(invocation -> {
            return invocation.<Drug>getArgument(0);
        });
        // when
        AddDrugsResponse res = drugAndPharmacyApplicationService.addDrugs(request);
        // then
        assert "testName".equals(res.getName());
        assert res.getStock().equals(50);
    }

    @Test
    void addExistDrugs() {
        // given
        Drug existDrug = new Drug();
        existDrug.setName("testName");
        existDrug.setStock(40);
        when(drugRepository.findByName("testName")).thenReturn(Optional.of(existDrug));
        AddDrugRequest request = new AddDrugRequest("testName", "manufacturer", "N110", LocalDate.now(), 50);
        when(drugRepository.save(any(Drug.class))).thenAnswer(invocation -> {
            return invocation.<Drug>getArgument(0);
        });
        // when
        AddDrugsResponse res = drugAndPharmacyApplicationService.addDrugs(request);
        // then
        assert "testName".equals(res.getName());
        assert res.getStock().equals(90);
    }

    @Test
    void listPharmacies() {
        // given
        Pharmacy pharmacy = new Pharmacy(1L, "testPharmacy", "address", Map.of(1L, 50));
        when(pharmacyRepository.findAll()).thenReturn(List.of(pharmacy));
        Drug drug = new Drug();
        drug.setId(1L);
        drug.setName("testName");
        drug.setStock(100);
        when(drugRepository.findAllById(any())).thenReturn(List.of(drug));
        // when
        List<PharmacyInfoResponse> res = drugAndPharmacyApplicationService.listPharmacies();
        // then
        PharmacyInfoResponse infoResponse = res.get(0);
        assert res.size() == 1;
        assert "testPharmacy".equals(infoResponse.getPharmacyName());
        assert "address".equals(infoResponse.getPharmacyAdd());
        assert infoResponse.getDrugsInfo().get("testName").equals(50);
    }
}