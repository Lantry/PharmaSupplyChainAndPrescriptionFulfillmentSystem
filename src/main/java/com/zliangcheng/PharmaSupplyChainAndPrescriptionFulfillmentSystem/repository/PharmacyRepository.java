package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
