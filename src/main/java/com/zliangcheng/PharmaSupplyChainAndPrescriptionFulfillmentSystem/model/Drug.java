package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.AddDrugRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugExpiryException;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.DrugInsufficientStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "drugs")
@Getter
@Setter
@NoArgsConstructor
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false, unique = true)
    private String batchNumber;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Integer stock;

    public static Drug from(AddDrugRequest request) {
        Drug drug = new Drug();
        drug.setName(request.getName());
        drug.setManufacturer(request.getManufacturer());
        drug.setBatchNumber(request.getBatchNumber());
        drug.setExpiryDate(request.getExpiryDate());
        drug.setStock(0);
        return drug;
    }

    public void addStock(Integer stock) {
        Integer curStock = getStock();
        curStock += stock;
        setStock(curStock);
    }

    public boolean isWithinExpiryDate() {
        return LocalDate.now().isBefore(expiryDate);
    }

    public void validateDrug(Integer quantity) {
        if (!isWithinExpiryDate()) {
            throw new DrugExpiryException(id, expiryDate);
        }
        if (stock < quantity) {
            throw new DrugInsufficientStockException(id);
        }
    }

    public void deductStock(int quantity) {
        validateDrug(quantity);
        stock -= quantity;
    }
}
