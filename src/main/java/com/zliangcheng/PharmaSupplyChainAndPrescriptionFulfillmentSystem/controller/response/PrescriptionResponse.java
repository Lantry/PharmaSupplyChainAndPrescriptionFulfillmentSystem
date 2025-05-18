package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.response;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {
    private Map<Long, Integer> drugIds;
    private String pharmacyId;
    private String prescriptionId;
    private String patientId;
    private ResponseStatus status;
    private String errorMessage;

    public static PrescriptionResponse from(Prescription prescription, String errorMessage, ResponseStatus status) {
        return new PrescriptionResponse(prescription.getDrugs(),
                prescription.getPharmacyId().toString(),
                prescription.getId().toString(),
                prescription.getPatientId(),
                status,
                errorMessage);
    }

    public static PrescriptionResponse emptyPrescription(String prescriptionId) {
        return new PrescriptionResponse(Map.of(),
                "",
                prescriptionId,
                "",
                ResponseStatus.FAILED,
                "Prescription not found with ID: " + prescriptionId);
    }
}
