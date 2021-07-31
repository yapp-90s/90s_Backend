package com.yapp.ios2.config.init.properties;

import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.FilmType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConfigurationProperties("film")
@Getter
public class FilmProps {

    private final List<FilmType> films;

    public FilmProps(List<FilmType> films) {
        this.films = films;
    }
}
