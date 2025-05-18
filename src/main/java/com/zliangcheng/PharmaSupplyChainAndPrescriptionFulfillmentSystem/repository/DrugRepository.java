package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.repository;

import com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug, Long> {

}
