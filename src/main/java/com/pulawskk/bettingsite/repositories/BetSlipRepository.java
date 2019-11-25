package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.BetSlip;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BetSlipRepository extends JpaRepositoryImplementation<BetSlip, Long> {
}
