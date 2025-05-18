package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
