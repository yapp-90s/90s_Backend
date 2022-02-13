package com.yapp.ios2.init;

import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.vo.FilmType;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultDataCreator {

    @Autowired
    FilmTypeRepository filmTypeRepository;

    @Autowired
    FilmProps filmProps;


    private void createFilmType() throws Exception{

        for(FilmType filmTypeProp : filmProps.getFilms()){
            FilmType filmType = filmTypeRepository.findFilmTypeByCode(filmTypeProp.getCode()).orElse(
                    FilmType.builder()
                            .name(filmTypeProp.getName())
                            .code(filmTypeProp.getCode())
                            .description(filmTypeProp.getDescription())
                            .max(filmTypeProp.getMax())
                            .build()
            );
            filmTypeRepository.save(filmType);
        }
    }

}
