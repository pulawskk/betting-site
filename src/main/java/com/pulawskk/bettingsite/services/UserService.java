package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.User;

public interface UserService {
    User findByEmail(String email);

    User findByName(String name);

    User userLoggedIn();

    String displayAuthName();
}
