package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
