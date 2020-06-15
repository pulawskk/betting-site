package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import com.pulawskk.bettingsite.repositories.GameRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    GameRepository gameRepository;

    Game game;

    @BeforeEach
    void setUp() {
        game = Game.builder()
                .id(11L)
                .start_date(LocalDateTime.now())
                .end_date(LocalDateTime.now())
                .competition("Premier League")
                .name("Chelsea vs Arsenal")
                .gameStatus(GameStatus.PREMATCH)
                .selection("3.59;6.04;1.26")
                .build();
    }

    @Test
    void shouldSavePrematchGame_whenPrematchGameIsSaved() {
        //given
        when(gameRepository.save(any())).thenReturn(game);

        //when
        gameService.savePrematchGame(any());

        //then
        verify(gameRepository, times(1)).save(any());
    }

    @Test
    void updateGameStatus() {
        //given
        String gameStatus = "RESULTED";
        String gameId = "12345";
        doNothing().when(gameRepository).updateGameStatus(gameStatus, gameId);

        //when
        gameService.updateGameStatus(gameStatus, gameId);

        //then
        verify(gameRepository, times(1)).updateGameStatus(gameStatus, gameId);
    }

    @Test
    void shouldPersistResult_whenResultIsPassedForSpecificGameId() {
        //given
        String jsonResult = "";
        String gameId = "11";

        doNothing().when(gameRepository).persistResults(jsonResult, gameId);

        //when
        gameService.persistResult(jsonResult, gameId);

        //then
        verify(gameRepository, times(1)).persistResults(jsonResult, gameId);

    }

    @Test
    void shouldReturnSetOfAllPrematchGames_whenExistInDb() {
        //given
        Set<Game> games = new HashSet<>();
        games.add(game);

        when(gameRepository.findAllByGameStatusPrematch()).thenReturn(games);

        //when
        Set<Game> actualGames = gameService.findAllPrematchGames();

        //then
        assertThat(actualGames.size(), is(games.size()));
        verify(gameRepository, times(1)).findAllByGameStatusPrematch();
    }

    @Test
    void shouldReturnSetOfAllCompletedGames_whenExistInDb() {
        //given
        Set<Game> games = new HashSet<>();
        game.setGameStatus(GameStatus.COMPLETED);
        games.add(game);

        when(gameRepository.findAllByGameStatusCompleted()).thenReturn(games);

        //when
        Set<Game> actualGames = gameService.findAllCompletedGames();

        //then
        assertThat(actualGames.size(), is(games.size()));
        verify(gameRepository, times(1)).findAllByGameStatusCompleted();
    }

    @Test
    void shouldReturnGame_whenGameWithSpecificIdExistsInDb() {
        //given
        String uniqueId = "abc123";
        game.setUniqueId(uniqueId);
        when(gameRepository.findGameByUniqueId(uniqueId)).thenReturn(game);

        //when
        Game actualGame = gameService.findGameById(uniqueId);

        //then
        assertThat(actualGame.getId(), is(11L));
        assertThat(actualGame.getUniqueId(), is(uniqueId));
        verify(gameRepository, times(1)).findGameByUniqueId(uniqueId);
    }

    @Test
    void shouldReturnListOfGamesForSpecificTeam_whenExistInDb() {
        //given
        String teamName = "Chelsea";
        List<Game> gameList = Lists.newArrayList(game);
        when(gameRepository.findAllByNameContaining(teamName)).thenReturn(gameList);

        //when
        List<Game> actualGames = gameService.findAllGamesForSpecificTeam(teamName);

        //then
        assertThat(actualGames.size(), is(gameList.size()));
        verify(gameRepository, times(1)).findAllByNameContaining(teamName);
    }
}