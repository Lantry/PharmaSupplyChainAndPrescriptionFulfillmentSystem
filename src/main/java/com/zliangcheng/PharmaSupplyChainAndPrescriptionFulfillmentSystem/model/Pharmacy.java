package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

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

    public boolean unableDispense(Drug drug, int quantity) {
        return !drugAllocations.containsKey(drug.getId()) || drugAllocations.get(drug.getId()) < quantity;
    }

    public void validateDrugQuantities(Drug drug, Integer quantity) {
        if (unableDispense(drug, quantity)) {
            throw new PharmacyException(id, drug.getId());
        }
    }

    public void deductAllocation(Drug drug, int quantity) {
        if (unableDispense(drug, quantity)) {
            throw new PharmacyException(id, drug.getId());
        }

        int remaining = drugAllocations.get(drug.getId()) - quantity;
        drugAllocations.put(drug.getId(), remaining);
    }
}
