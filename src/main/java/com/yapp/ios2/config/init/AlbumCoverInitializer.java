package com.yapp.ios2.config.init;

import com.yapp.ios2.repository.AlbumCoverRepository;
import com.yapp.ios2.vo.AlbumCover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AlbumCoverInitializer {

    @Autowired
    AlbumCoverRepository coverRepository;

    public void run(String... args) throws Exception {
        List<String> covers = Arrays.asList(
                "1990",
                "paradiso",
                "happilyeverafter",
                "favoritethings",
                "awsomemix",
                "lessbutbetter",
                "90sretroclub",
                "oneandonly"
        );
        if(coverRepository.findAll().isEmpty()){
            for(int i = 0; i < covers.size(); i++){
                AlbumCover albumCover = coverRepository.findById(Long.valueOf(i+1)).orElse(
                        AlbumCover.builder()
                                .uid(Long.valueOf(i+1))
                                .name(covers.get(i))
                                .path("static/" + covers.get(i) + ".jpeg")
                                .build()
                );
                coverRepository.save(albumCover);
            }
        }
    }
}
