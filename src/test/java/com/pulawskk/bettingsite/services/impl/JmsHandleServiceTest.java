package com.pulawskk.bettingsite.services.impl;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JmsHandleServiceTest {

    @InjectMocks
    JmsHandleService jmsHandleService;

    @Mock
    GameServiceImpl gameService;

    @Mock
    ConnectionFactory connectionFactory;

    @BeforeEach
    void setUp() throws IOException {
    }

    @Disabled
    void receiveGameData() {
    }

    @Disabled
    void receiveResultData() {
    }

    @Disabled
    void receiveGameDataJms() {
    }

    @Disabled
    void receiveResultDataJms() {
    }
}