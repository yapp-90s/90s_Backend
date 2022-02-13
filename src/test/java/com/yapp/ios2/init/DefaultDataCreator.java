package com.yapp.ios2.init;

import com.yapp.ios2.config.init.properties.CoverProps;
import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.config.init.properties.LayoutProps;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.FilmType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultDataCreator extends TestInit {

    @Autowired
    FilmTypeRepository filmTypeRepository;

    @Autowired
    FilmProps filmProps;

    @Autowired
    CoverProps coverProps;

    @Autowired
    LayoutProps layoutProps;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void createFilmType() throws Exception{

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

    @Test
    public void createAlbumCover() throws Exception{

    }

    @Test
    public void createAlbumLayout() throws Exception{

    }
}
