//package com.yapp.ios2.config.init;
//
//
//import com.yapp.ios2.config.init.properties.FilmProps;
//import com.yapp.ios2.repository.FilmTypeRepository;
//import com.yapp.ios2.vo.FilmType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class FilmTypeInitializer {
//
//    @Autowired
//    FilmTypeRepository filmTypeRepository;
//
//    @Autowired
//    FilmProps filmProps;
//
//    public void run(String ... args) throws Exception {
//
//        for(FilmType filmTypeProp : filmProps.getFilms()){
//            FilmType filmType = filmTypeRepository.findFilmTypeByCode(filmTypeProp.getCode()).orElse(
//                    FilmType.builder()
//                            .name(filmTypeProp.getName())
//                            .code(filmTypeProp.getCode())
//                            .description(filmTypeProp.getDescription())
//                            .max(filmTypeProp.getMax())
//                            .build()
//            );
//            filmTypeRepository.save(filmType);
//        }
//    }
//
//}
