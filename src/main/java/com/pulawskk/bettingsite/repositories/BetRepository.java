package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    @Query(value = "SELECT * FROM bet WHERE bet_status LIKE 'PREMATCH'", nativeQuery = true)
    List<Bet> findAllPrematchBets();

    @Query(value = "SELECT * FROM bet WHERE bet_status LIKE 'RESULTED'", nativeQuery = true)
    List<Bet> findAllResultedBets();

    List<Bet> findAllBySelectionId(String uniqueId);
}
