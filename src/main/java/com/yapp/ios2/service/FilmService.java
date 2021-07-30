package com.yapp.ios2.service;


import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.config.exception.InvalidValueException;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    FilmRepository filmRepository;
    @Autowired
    FilmTypeRepository filmTypeRepository;
    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    S3Service s3Service;

    public Film createFilm(Integer filmCode, String name, User user){

        Film film = Film.builder()
                .name(name)
                .filmType(filmTypeRepository.findFilmTypeByCode(filmCode).orElseThrow(
                        () -> new InvalidValueException("Invalid Film Type Uid")
                ))
                .user(user)
                .build();

        filmRepository.save(film);

        return film;
    }

    public List<Film> getFilms(User user){

        filmRepository.findAll();

        return filmRepository.findAllByUser(user);
    }

    public Film startPrinting(Long filmUid){
        Film film = filmRepository.findById(filmUid).get();

//      일단 인화 기간은 3일로 합니다.
        LocalDateTime now = LocalDateTime.now();
        film.setPrintStartAt(now);
        film.setPrintEndAt(now.plusDays(3));
        filmRepository.save(film);

        return film;

    }

}
