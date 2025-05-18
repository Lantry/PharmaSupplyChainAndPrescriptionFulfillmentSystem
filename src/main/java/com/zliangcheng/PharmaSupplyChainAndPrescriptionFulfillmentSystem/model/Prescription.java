package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.exception.PrescriptionInvalidException;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.utils.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pharmacyId;

    @Column(name = "quantity")
    @Convert(converter = MapToJsonConverter.class)
    private Map<Long, Integer> drugs = new HashMap<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    @Column(length = 1000)
    private String errorMessage;

    public Prescription(String pharmacyId, Map<Long, Integer> drugs) {
        this.pharmacyId = Long.valueOf(pharmacyId);
        this.drugs = drugs;
        this.status = PrescriptionStatus.PENDING;
    }

    public void fulfill() {
        this.status = PrescriptionStatus.PROCESSED;
    }

    public void invalid() {
        this.status = PrescriptionStatus.REJECTED;
    }

    public void validateOrderForFulfillment() {
        if (!status.equals(PrescriptionStatus.PENDING)) {
            throw new PrescriptionInvalidException(id, status.getDescription());
        }
    }
}
