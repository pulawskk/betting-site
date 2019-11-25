package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.BetLeg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetLegRepository extends JpaRepository<BetLeg, Long> {
}
