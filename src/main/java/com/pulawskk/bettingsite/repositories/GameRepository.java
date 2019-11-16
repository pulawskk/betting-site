package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Modifying
    @Query(value = "UPDATE game SET game_status = ?1 WHERE id = ?2 AND game_status = 'PREMATCH'", nativeQuery = true)
    void updateGameStatus(String gameStatus, Long gameId);
}
