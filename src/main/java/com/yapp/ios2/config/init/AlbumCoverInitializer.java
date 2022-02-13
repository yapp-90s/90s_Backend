//package com.yapp.ios2.config.init;
//
//import com.yapp.ios2.config.init.properties.CoverProps;
//import com.yapp.ios2.repository.AlbumCoverRepository;
//import com.yapp.ios2.vo.AlbumCover;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class AlbumCoverInitializer {
//
//    @Autowired
//    AlbumCoverRepository coverRepository;
//    @Autowired
//    CoverProps coverProps;
//
//    public void run(String... args) throws Exception {
//        for( AlbumCover albumCoverProp : coverProps.getCovers()){
//            AlbumCover albumCover = coverRepository.findAlbumCoverByCode(albumCoverProp.getCode()).orElse(
//                    AlbumCover.builder()
//                            .code(albumCoverProp.getCode())
//                            .name(albumCoverProp.getName())
//                            .build()
//            );
//            coverRepository.save(albumCover);
//        }
//
//    }
//}
