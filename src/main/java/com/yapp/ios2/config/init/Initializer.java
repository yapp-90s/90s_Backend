package com.yapp.ios2.config.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer  implements CommandLineRunner {

    @Autowired
    DefaultUserInitializer defaultUserInitializer;

    @Autowired
    FilmTypeInitializer filmTypeInitializer;

    @Autowired
    AlbumCoverInitializer albumCoverInitializer;

    @Autowired
    AlbumLayoutInitializer albumLayoutInitializer;

    @Override
    public void run(String... args) throws Exception {

//        !! 실행 순서 중요 !!

        filmTypeInitializer.run();

        albumCoverInitializer.run();

        albumLayoutInitializer.run();

////        디폴트 유저 및 테스트 유저 관련 설정
        defaultUserInitializer.run();

    }
}
