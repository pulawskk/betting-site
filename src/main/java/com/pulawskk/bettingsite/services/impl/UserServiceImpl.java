package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.repositories.UserRepository;
import com.pulawskk.bettingsite.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User userLoggedIn() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email);
    }

    public String displayAuthName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
