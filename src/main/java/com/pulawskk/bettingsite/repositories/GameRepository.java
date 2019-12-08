package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Modifying
    @Query(value = "UPDATE game SET game_status = ?1 WHERE unique_id like ?2", nativeQuery = true)
    void updateGameStatus(String gameStatus, String uniqueId);

    @Modifying
    @Query(value = "UPDATE game SET result = ?1 WHERE unique_id like ?2", nativeQuery = true)
    void persistResults(String jsonResult, String uniqueId);

    @Query(value = "SELECT * FROM game WHERE game_status LIKE 'PREMATCH'", nativeQuery = true)
    Set<Game> findAllByGameStatusPrematch();

    @Query(value = "SELECT * FROM game WHERE game_status LIKE 'COMPLETED'", nativeQuery = true)
    Set<Game> findAllByGameStatusCompleted();

    Game findGameByUniqueId(String uniqueId);
}
