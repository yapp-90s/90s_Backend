package com.yapp.ios2.controller;

import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public class TestFunc {

    public static User createTester(UserRepository userRepository, PasswordEncoder passwordEncoder){
        User testUser = userRepository.findUserByPhone("010-9523-3114").orElse(
                User.builder()
                        .emailKakao("tester@90s.com")
                        .emailGoogle("tester@90s.com")
                        .emailApple("tester@90s.com")
                        .name("90s_tester")
                        .roles(Collections.singletonList("ROLE_TESTER"))
                        .build()
        );
        userRepository.save(testUser);
        return testUser;
    }

    public static void deleteTester(UserService userService, User user){

        userService.delete(user);
    }

}
