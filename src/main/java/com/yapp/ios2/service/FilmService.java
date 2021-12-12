package com.yapp.ios2.service;


import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.config.exception.InvalidValueException;
import com.yapp.ios2.dto.BooleanDto;
import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.dto.StartPrintingDto;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.FilmTypeRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FilmService {

    @Value("${ai.hostname}")
    String aiHostName;

    @Autowired
    FilmRepository filmRepository;
    @Autowired
    FilmTypeRepository filmTypeRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    PhotoService photoService;
    @Autowired
    HttpService httpService;
    @Autowired
    S3Service s3Service;

    public FilmDto createFilm(Integer filmCode, String name, User user){

        Film film = Film.builder()
                .name(name)
                .filmType(filmTypeRepository.findFilmTypeByCode(filmCode).orElseThrow(
                        () -> new InvalidValueException("Invalid Film Type Uid")
                ))
                .user(user)
                .build();

        filmRepository.save(film);

        FilmDto filmDto = new FilmDto(film);

        return filmDto;
    }

    public List<FilmDto> getFilms(User user){
        // 삭제 되지 않은 필름만 가져옴.
        // 삭제 되지 않은 필름은 deleteAt 이 Null.

        List<FilmDto> filmDtos = new ArrayList<>();

        filmRepository.findAllByUserAndDeleteAt(user).forEach(
            film -> {
                filmDtos.add(new FilmDto(film));
            }
        );

        return filmDtos;
    }

    public BooleanDto startPrinting(Long filmUid) throws JsonProcessingException{

        Film film = filmRepository.findById(filmUid).get();

        ObjectMapper json = new ObjectMapper();

        String jsonString;

        StartPrintingDto data = StartPrintingDto.builder()
                .film_code(film.getFilmType().getCode())
                .film_uid(film.getUid().intValue())
                .build();


        for(Photo photo : photoRepository.findAllByFilm(film)){
            data.setPhoto_uid(photo.getUid().intValue());

            jsonString = json.writeValueAsString(data);

            if(httpService.sendReq(aiHostName, jsonString)){
                continue;
            }else{
                return BooleanDto.builder().result(false).build();
            }
        }



//      일단 인화 기간은 3일로 합니다.
        LocalDateTime now = LocalDateTime.now();
        film.setPrintStartAt(now);
        film.setPrintEndAt(now.plusDays(3));
        filmRepository.save(film);

        return BooleanDto.builder().result(true).build();

    }

    public BooleanDto delete(Long filmUid) throws IOException {

        try {
            Film film = filmRepository.findById(filmUid).orElseThrow(
                    () -> new EntityNotFoundException("No Film with " + filmUid.toString() + " Uid. Wrong FilmUid.")
            );

            film.setDeleteAt(LocalDateTime.now());

            filmRepository.save(film);

            return BooleanDto.success();


        } catch (Exception e) {
            return BooleanDto.fail(e.getMessage());
        }
    }

    public void changePrintStartAt(Long filmUid, LocalDateTime dateTime){

        Film film = filmRepository.findById(filmUid).get();
        film.setPrintStartAt(dateTime);
        filmRepository.save(film);

    }
    public void changePrintEndAt(Long filmUid, LocalDateTime dateTime){

        Film film = filmRepository.findById(filmUid).get();
        film.setPrintEndAt(dateTime);
        filmRepository.save(film);

    }

    public Film getFilmByPhoto(Long photoUid){

        return photoRepository.findById(photoUid).get().getFilm();

    }
}
