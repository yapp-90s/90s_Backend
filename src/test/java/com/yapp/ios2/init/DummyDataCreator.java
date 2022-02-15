package com.yapp.ios2.init;

import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.FilmType;
import com.yapp.ios2.vo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DummyDataCreator extends TestInit {

    @Test
    public void createDummyUser(){

        for (int i = 1; i < 5; i++) {
            String name = "tester" + i;

            if (userRepository.findUserByPhone(String.format("010-0000-%04d", i)).isPresent()) continue;

            User testUser = userRepository.findUserByPhone(String.format("010-0000-%04d", i)).orElse(
                    User.builder()
                            .emailKakao(name + "@90s.com")
                            .emailApple(name + "@90s.com")
                            .emailGoogle(name + "@90s.com")
                            .name("90s_" + name)
                            .phoneNum(String.format("010-0000-%04d", i))
                            .roles(Collections.singletonList("ROLE_TESTER"))
                            .build()
            );

            userRepository.save(testUser);
        }
    }



}
