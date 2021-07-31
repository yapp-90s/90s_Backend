package com.yapp.ios2.config.init.properties;

import com.yapp.ios2.vo.AlbumCover;
import com.yapp.ios2.vo.AlbumLayout;
import com.yapp.ios2.vo.FilmType;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("album")
@Getter
public class AlbumProps {

    private final List<AlbumCover> covers;
    private final List<AlbumLayout> layouts;

    public AlbumProps(List<AlbumCover> covers, List<AlbumLayout> layouts) {
        this.covers = covers;
        this.layouts = layouts;
    }
}
