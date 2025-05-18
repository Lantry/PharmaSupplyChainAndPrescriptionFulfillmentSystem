package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.utils.ListToJsonConverter;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.utils.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long patientId;

    @Column
    private Long pharmacyId;

    @Column
    private Long prescriptionId;

    @Column
    @Convert(converter = ListToJsonConverter.class)
    private List<String> drugsRequested;

    @Column
    @Convert(converter = MapToJsonConverter.class)
    private Map<Long, Integer> drugsDispensed;

    @Column
    private String failureReason = "";

    @CreatedDate
    private LocalDateTime attemptTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseStatus status;
}
