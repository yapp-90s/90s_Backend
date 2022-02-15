package com.yapp.ios2.init;

import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.Film;
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

    @Test
    public void createDummyFilmsToAllUser() {
        List<User> users = userRepository.findAll();

        users.forEach(
                user -> {
                    createDummyFilms(user);
                }
        );

    }

    @Test
    public void startDevelopingOfUsersFilms(){
        List<User> users = userRepository.findAll();

        users.forEach(
            user -> {
                List<Film> films = filmRepository.findAllByUser(user);

                // 필름들 중 절반만 인화함
                for(int i = 0; i < films.size(); i++){

                    if(i % 2 == 0) continue;
                    try{
                        startDevelopingFilm(films.get(i)); // 인화 기간 0 으로 해서 인화 시작
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        );
    }


}
