package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddDrugsResponse {
    private String name;
    private Integer stock;

    public AddDrugsResponse(String name, Integer stock) {
        this.name = name;
        this.stock = stock;
    }
}
