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

    public Photo upload(MultipartFile photo, Long filmUid) throws IOException {



        Photo newPhoto = Photo.builder()
                .film(filmRepository.findById(filmUid).orElseThrow(
                        () -> new EntityNotFoundException("Invalid FilmUid")
                ))
                .build();

        photoRepository.save(newPhoto);

        String fileName = filmUid.toString() + "/" + newPhoto.getUid() + ".jpeg";

        String url = s3Service.upload(photo, fileName);

        newPhoto.setUrl(url);

        photoRepository.save(newPhoto);

        return newPhoto;
    }

}
