package org.bloggerapp.bloggerapp;

import org.bloggerapp.bloggerapp.repositories.UserRepository;
import org.bloggerapp.bloggerapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSample {
    @Autowired
    UserRepository userRepository;

    @Test
    public void sample() {
        System.out.println(userRepository.getClass());
        System.out.println(userRepository.getClass().getName());
        System.out.println(userRepository.getClass().getPackage());
    }
}
