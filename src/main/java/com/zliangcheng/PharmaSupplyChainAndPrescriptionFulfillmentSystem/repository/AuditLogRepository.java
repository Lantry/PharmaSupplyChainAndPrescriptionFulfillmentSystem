package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
