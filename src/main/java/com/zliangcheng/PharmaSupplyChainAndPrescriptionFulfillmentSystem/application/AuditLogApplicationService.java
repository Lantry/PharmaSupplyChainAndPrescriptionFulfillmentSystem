package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.application;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.controller.request.FindAuditLogRequest;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.AuditLog;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.ResponseStatus;
import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuditLogApplicationService {
    private final AuditLogRepository auditLogRepository;

    public List<AuditLog> findAllAuditLog(FindAuditLogRequest request) {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return auditLogs.stream().filter(auditLog -> {
            if (Strings.isNotBlank(request.getPharmacyId())
                    && !String.valueOf(auditLog.getPharmacyId()).equals(request.getPharmacyId())) {
                return false;
            }

            if (Strings.isNotBlank(request.getPatientId())
                    && !String.valueOf(auditLog.getPatientId()).equals(request.getPatientId())) {
                return false;
            }

            if (Objects.nonNull(request.getStatus())) {
                return auditLog.getStatus().equals(request.getStatus());
            }

            return true;
        }).toList();
    }

    public List<AuditLog> findAllAuditLog(Long patientId, Long pharmacyId, ResponseStatus status) {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return auditLogs.stream().filter(auditLog -> {
            if (Objects.nonNull(patientId)
                    && !Objects.equals(auditLog.getPatientId(), patientId)) {
                return false;
            }

            if (Objects.nonNull(pharmacyId)
                    && !Objects.equals(auditLog.getPharmacyId(), pharmacyId)) {
                return false;
            }

            if (Objects.nonNull(status)) {
                return auditLog.getStatus().equals(status);
            }

            return true;
        }).toList();
    }
}
