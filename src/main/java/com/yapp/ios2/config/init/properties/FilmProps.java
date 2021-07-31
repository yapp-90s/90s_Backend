package com.yapp.ios2.config.init.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "films")
@Getter
@Setter
@Data
public class FilmProps {

    private List<FilmProp> filmProps;

    @Getter
    @Setter
    public class FilmProp{
        private String name;
        private Integer code;
        private Integer max;
    }
}
