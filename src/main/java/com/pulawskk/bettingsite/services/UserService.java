package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByEmail(String email);

    User userLoggedIn();

    String displayAuthName();
}
