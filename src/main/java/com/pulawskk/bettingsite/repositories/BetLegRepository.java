package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.BetLeg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetLegRepository extends JpaRepository<BetLeg, Long> {
    @Query(value = "SELECT * FROM betleg WHERE result IS NULL", nativeQuery = true)
    List<BetLeg> findAllUnresulted();
}
