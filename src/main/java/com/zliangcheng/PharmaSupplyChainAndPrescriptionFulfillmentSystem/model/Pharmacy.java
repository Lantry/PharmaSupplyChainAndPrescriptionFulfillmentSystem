package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugException;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.PharmacyException;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.utils.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "pharmacies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Convert(converter = MapToJsonConverter.class)
    private Map<Long, Integer> drugAllocations = new HashMap<>();

    public boolean canDispense(Drug drug, int quantity) {
        try {
            drug.validateDrug(quantity);
        } catch (DrugException exception) {
            return false;
        }

        return drugAllocations.containsKey(drug.getId()) && drugAllocations.get(drug.getId()) >= quantity;
    }

    public void validateDrugQuantities(Drug drug, Integer quantity) {
        if (!canDispense(drug, quantity)) {
            throw new PharmacyException(id, drug.getId());
        }
    }
}
