package com.yapp.ios2.config.init.properties;

import com.yapp.ios2.vo.AlbumCover;
import com.yapp.ios2.vo.AlbumLayout;
import com.yapp.ios2.vo.FilmType;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("cover")
@Getter
public class CoverProps {

    private final List<AlbumCover> covers;

    public CoverProps(List<AlbumCover> covers) {
        this.covers = covers;
    }
}
