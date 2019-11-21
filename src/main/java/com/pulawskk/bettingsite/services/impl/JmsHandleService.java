package com.pulawskk.bettingsite.services.impl;

import com.google.gson.Gson;
import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.Result;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.IncomingDataService;
import com.pulawskk.bettingsite.utils.GameUtils;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JmsHandleService implements IncomingDataService, GameUtils {

    private static final String GAME_QUEUE = "FA CUP prematch";
    private static final String RESULT_QUEUE = "FA CUP result";

    private final GameService gameService;

    private final ConnectionFactory connectionFactory;
    private final Connection connection;
    private final Channel channelGame;
    private final Channel channelResult;

    public JmsHandleService(GameService gameService, ConnectionFactory connectionFactory) throws IOException {
        this.gameService = gameService;
        this.connectionFactory = connectionFactory;
        this.connection = this.connectionFactory.newConnection();
        this.channelGame = this.connection.createChannel();
        this.channelResult = this.connection.createChannel();
        this.channelGame.queueDeclare(GAME_QUEUE, false, false, false, null);
        this.channelResult.queueDeclare(RESULT_QUEUE, false, false, false, null);
    }

    @Override
    public void receiveGameData(GameDto gameDto) {

    }

    @Override
    public void receiveResultData(ResultDto resultDto) {

    }

    public void receiveGameDataJms() throws IOException {
        Gson gson = new Gson();
        Consumer consumer = new DefaultConsumer(this.channelGame) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                GameDto gameDto = gson.fromJson(message, GameDto.class);
                System.out.println("JMS game message: " + message);
                System.out.println(gameDto.getTeamHome() + " VS " + gameDto.getTeamAway());

                Game game = processGameFromGameDto(gameDto);
                gameService.savePrematchGame(game);
            }
        };
        channelGame.basicConsume(GAME_QUEUE, true, consumer);
    }

    public void receiveResultDataJms() throws IOException {
        Gson gson = new Gson();
        Consumer consumer = new DefaultConsumer(this.channelGame) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                ResultDto resultDto = gson.fromJson(message, ResultDto.class);
                System.out.println("JMS result message: " + message);
                System.out.println(resultDto.getTeamHome() + " VS " + resultDto.getTeamAway());

                final String jsonResultString = processResultFromResultDto(resultDto);
                final String uniqueId = resultDto.getUniqueId();
                final String statusToUpdate = "COMPLETED";

                gameService.persistResult(jsonResultString, uniqueId);
                gameService.updateGameStatus(statusToUpdate, uniqueId);
            }
        };
        channelGame.basicConsume(RESULT_QUEUE, true, consumer);
    }
}
