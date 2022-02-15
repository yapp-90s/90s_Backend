package com.yapp.ios2.init;

import com.yapp.ios2.config.init.properties.CoverProps;
import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.config.init.properties.LayoutProps;
import com.yapp.ios2.repository.AlbumCoverRepository;
import com.yapp.ios2.repository.AlbumLayoutRepository;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.AlbumCover;
import com.yapp.ios2.vo.AlbumLayout;
import com.yapp.ios2.vo.FilmType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultDataCreator extends TestInit {

    @Autowired
    FilmTypeRepository filmTypeRepository;
    @Autowired
    AlbumLayoutRepository albumLayoutRepository;
    @Autowired
    AlbumCoverRepository albumCoverRepository;


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
        for( AlbumCover albumCoverProp : coverProps.getCovers()){
            AlbumCover albumCover = albumCoverRepository.findAlbumCoverByCode(albumCoverProp.getCode()).orElse(
                    AlbumCover.builder()
                            .code(albumCoverProp.getCode())
                            .name(albumCoverProp.getName())
                            .build()
            );
            albumCoverRepository.save(albumCover);
        }
    }

    @Test
    public void createAlbumLayout() throws Exception{
        for(AlbumLayout albumLayoutProp : layoutProps.getLayouts()){
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
