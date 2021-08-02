package com.yapp.ios2.config.init;

import com.yapp.ios2.config.FuncUtils;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DummyDataInitializer {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoService photoService;
    @Autowired
    FilmService filmService;
    @Autowired
    AlbumService albumService;
    @Autowired
    FilmProps filmProps;

    public void run(String... args) throws Exception {

        List<User> users = new ArrayList<User>();

        if (!userRepository.findUserByPhone("010-0000-0000").isPresent()) {

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
            users.add(defaultUser);

//        TESTER INITIALIZER

            User defaultTestUser = userRepository.findUserByPhone("010-1234-1234").orElse(
                    User.builder()
                            .emailKakao("tester@90s.com")
                            .emailGoogle("tester@90s.com")
                            .emailApple("tester@90s.com")
                            .name("90s_tester")
                            .phoneNum("010-1234-1234")
                            .roles(Collections.singletonList("ROLE_TESTER"))
                            .build()
            );
            userRepository.save(defaultTestUser);
            System.out.println("DEFAULT Test USER JWT TOKEN : " + jwtProvider.createToken(defaultUser.getUid().toString(), defaultUser.getRoles()));
            users.add(defaultTestUser);

            for (int i = 0; i < 5; i++) {
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
                System.out.println(name + " JWT TOKEN : " + jwtProvider.createToken(testUser.getUid().toString(), testUser.getRoles()));
                users.add(testUser);
            }

            for (User user : users) {

                FuncUtils.createDummyFilms(filmProps.getFilms(), filmRepository, filmService, user);

                FuncUtils.addPhotoInFilmByUser(photoService, filmService, user);

                FuncUtils.createDummyAlbums(albumRepository, albumService, user);
            }

        } else {
            System.out.println("already exist");
        }
    }
}
