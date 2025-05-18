package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {
    private List<String> drugIds;
    private String pharmacyId;
    private String prescriptionId;
    private ResponseStatus status;
    private String errorMessage;

    public static PrescriptionResponse from(Prescription prescription, String errorMessage, ResponseStatus status) {
        List<String> drugIds = prescription.getDrugs().keySet().stream().map(String::valueOf).toList();
        return new PrescriptionResponse(drugIds,
                prescription.getPharmacyId().toString(),
                prescription.getId().toString(),
                status,
                errorMessage);
    }

    public static PrescriptionResponse emptyPrescription(String prescriptionId) {
        return new PrescriptionResponse(List.of(),
                "",
                prescriptionId,
                ResponseStatus.FAILED,
                "Prescription not found with ID: " + prescriptionId);
    }
}
