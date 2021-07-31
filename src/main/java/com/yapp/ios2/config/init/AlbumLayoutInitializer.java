package com.yapp.ios2.config.init;

import com.yapp.ios2.config.init.properties.AlbumProps;
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
    @Autowired
    AlbumProps albumProps;

    public void run(String... args) throws Exception {

        for(AlbumLayout albumLayoutProp : albumProps.getLayouts()){
            AlbumLayout albumLayout = albumLayoutRepository.findAlbumLayoutByCode(albumLayoutProp.getCode()).orElse(
                    AlbumLayout.builder()
                            .code(albumLayoutProp.getCode())
                            .name(albumLayoutProp.getName())
                            .totPaper(albumLayoutProp.getTotPaper())
                            .photoPerPaper(albumLayoutProp.getPhotoPerPaper())
                            .build()
            );
            albumLayoutRepository.save(albumLayout);
        }
    }
}
