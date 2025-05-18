package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class AddDrugRequest {
    @NotBlank(message = "drug name can not be blank")
    private String name;

    @NotBlank(message = "drug manufacturer can not be blank")
    private String manufacturer;

    @NotBlank(message = "batch number can not be blank")
    private String batchNumber;

    @NotNull(message = "expiry date can not be null")
    private LocalDate expiryDate;

    @Min(value = 1)
    private Integer stock;
}
