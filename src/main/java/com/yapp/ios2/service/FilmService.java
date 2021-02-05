package com.yapp.ios2.service;

import com.yapp.ios2.config.exception.InvalidValueException;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.vo.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    @Autowired
    FilmRepository filmRepository;
    @Autowired
    FilmTypeRepository filmTypeRepository;

    public Film createFilm(Long filmUid, String name){

        Film film = Film.builder()
                .name(name)
                .filmType(filmTypeRepository.findById(filmUid).orElseThrow(
                        () -> new InvalidValueException("Invalid Film Type Uid")
                ))
                .build();

        filmRepository.save(film);

        return film;
    }

}
