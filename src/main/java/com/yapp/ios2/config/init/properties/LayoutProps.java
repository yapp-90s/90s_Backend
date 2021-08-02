package com.yapp.ios2.config.init.properties;

import com.yapp.ios2.vo.AlbumCover;
import com.yapp.ios2.vo.AlbumLayout;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("layout")
@Getter
public class LayoutProps {
    private final List<AlbumLayout> layouts;

    public LayoutProps(List<AlbumLayout> layouts) {
        this.layouts = layouts;
    }
}
