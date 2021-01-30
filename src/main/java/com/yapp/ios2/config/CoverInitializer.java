//package com.yapp.ios2.config;
//
//import com.yapp.ios2.repository.CoverRepository;
//import com.yapp.ios2.vo.AlbumType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class CoverInitializer{
//
//    @Autowired
//    CoverRepository coverRepository;
//
//    public void run(String... args) throws Exception {
//        List<String> covers = Arrays.asList(
//                "1990",
//                "paradiso",
//                "happilyeverafter",
//                "favoritethings",
//                "awsomemix",
//                "lessbutbetter",
//                "90sretroclub",
//                "oneandonly"
//        );
//        if(coverRepository.findAll().isEmpty()){
//            for(int i = 0; i < covers.size(); i++){
//                AlbumType albumType = coverRepository.findById(Long.valueOf(i+1)).orElse(
//                        AlbumType.builder()
//                                .uid(Long.valueOf(i+1))
//                                .name(covers.get(i))
//                                .path("static/" + covers.get(i) + ".jpeg")
//                                .build()
//                );
//                coverRepository.save(albumType);
//            }
//        }
//    }
//}
