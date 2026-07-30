package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;

    public MailService getMailService() {
        return mailService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public String registerUser(String name) {
        System.out.println("registerUser "+name);
        return name;
    }
}
