package com.yapp.ios2.config;

import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class DefaultUserInitializer{
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PhotoRepository photoRepository;

    public void run(String... args) throws Exception {

        System.out.println("CREATE DEFAULT USER");
        User defaultUser = userRepository.findUserByPhone("010-0000-0000").orElse(
                User.builder()
                        .emailKakao("tryer@90s.com")
                        .emailGoogle("tryer@90s.com")
                        .emailApple("tryer@90s.com")
                        .phoneNum("010-0000-0000")
                        .name("90s_TRYER")
                        .roles(Collections.singletonList("ROLE_TRYER"))
                        .build()
        );
        userRepository.save(defaultUser);
        System.out.println("DEFAULT USER JWT TOKEN : " + jwtProvider.createToken(defaultUser.getUid().toString(), defaultUser.getRoles()));

//        TESTER INITIALIZER

        for(int i = 0; i < 5; i++){
            String name = "tester" + i;

            if(userRepository.findUserByPhone(String.format("010-1234-%04d",i)).isPresent()) continue;

            User testUser = userRepository.findUserByPhone(String.format("010-0000-%04d",i)).orElse(
                    User.builder()
                            .emailKakao(name + "@90s.com")
                            .emailApple(name + "@90s.com")
                            .emailGoogle(name + "@90s.com")
                            .name("90s_" + name)
                            .phoneNum(String.format("010-0000-%04d",i))
                            .roles(Collections.singletonList("ROLE_TESTER"))
                            .build()
            );

            userRepository.save(testUser);
            System.out.println(name + " JWT TOKEN : " + jwtProvider.createToken(testUser.getUid().toString(), testUser.getRoles()));

        }

    }
}
