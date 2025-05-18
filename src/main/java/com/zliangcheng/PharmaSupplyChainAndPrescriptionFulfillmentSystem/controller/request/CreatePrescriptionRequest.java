package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
public class CreatePrescriptionRequest {
    @NotBlank(message = "pharmacy id can not be blank")
    private String pharmacyId;

    private Map<Long, Integer> drugs;
}
