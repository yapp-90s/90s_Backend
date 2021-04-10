package com.yapp.ios2.config.init;


import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.vo.FilmType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FilmTypeInitializer {

    @Autowired
    FilmTypeRepository filmTypeRepository;

    public void run(String ... args) throws Exception {

        List<String> filmTypeNameList = Arrays.asList(
                "test0",
                "test1",
                "test2",
                "test3"
        );

        List<String> filmTypeDescList = Arrays.asList(
                "Description of test0",
                "Description of test1",
                "Description of test2",
                "Description of test3"
        );

        List<Integer> filmTypeCodeList = Arrays.asList(
                1001,
                1002,
                1003,
                1004
        );




        for(int i = 0; i < 4; i++){
            FilmType filmType = filmTypeRepository.findFilmTypeByCode(filmTypeCodeList.get(i)).orElse(
                    FilmType.builder()
                            .name(filmTypeNameList.get(i))
                            .code(filmTypeCodeList.get(i))
                            .description(filmTypeDescList.get(i))
                            .build()
            );
            filmTypeRepository.save(filmType);
        }

    }

}