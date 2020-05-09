package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.entities.BetSlip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetSlipRepository extends JpaRepositoryImplementation<BetSlip, Long> {

    @Query(value = "SELECT * FROM betslip as bs " +
            "WHERE bs.result IS NULL", nativeQuery = true)
    List<BetSlip> findAllUnresulted();

    @Query(value = "SELECT * FROM betslip as bs " +
            "WHERE bs.result IS NULL AND bs.user_id = ?1", nativeQuery = true)
    List<BetSlip> findBetSlipsByBetSlipStatusIsActiveAndUserId(Long userId);
}
