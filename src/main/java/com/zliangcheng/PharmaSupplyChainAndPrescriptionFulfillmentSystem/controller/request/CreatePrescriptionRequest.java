package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreatePrescriptionRequest {
    @NotBlank(message = "pharmacy id can not be blank")
    private String pharmacyId;

    @NotBlank(message = "patient id can not be blank")
    private String patientId;

    private Map<Long, Integer> drugs;
}
