package com.yapp.ios2.config.init;

import com.yapp.ios2.repository.AlbumCoverRepository;
import com.yapp.ios2.repository.AlbumLayoutRepository;
import com.yapp.ios2.vo.AlbumCover;
import com.yapp.ios2.vo.AlbumLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AlbumLayoutInitializer {

    @Autowired
    AlbumLayoutRepository albumLayoutRepository;

    public void run(String... args) throws Exception {
        List<String> layouts = Arrays.asList(
                "1990",
                "paradiso",
                "happilyeverafter",
                "favoritethings",
                "awsomemix",
                "lessbutbetter",
                "90sretroclub",
                "oneandonly"
        );
        List<Integer> albumLayoutCodes = Arrays.asList(
                1001,
                1002,
                1003,
                1004,
                2001,
                2002,
                2003,
                3004
        );
        if(albumLayoutRepository.findAll().isEmpty()){
            for(int i = 0; i < layouts.size(); i++){
                AlbumLayout albumLayout = albumLayoutRepository.findById(Long.valueOf(i+1)).orElse(
                        AlbumLayout.builder()
                                .uid(Long.valueOf(i+1))
                                .code(albumLayoutCodes.get(i))
                                .name(layouts.get(i))
                                .path("static/" + layouts.get(i) + ".jpeg")
                                .photoPerPaper(3)
                                .build()
                );
                albumLayoutRepository.save(albumLayout);
            }
        }
    }
}
