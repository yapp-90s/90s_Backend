package com.yapp.ios2.service;


import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.config.exception.InvalidValueException;
import com.yapp.ios2.config.info.PHOTO_TYPE;
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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService{

    @Value("${ai.hostname}")
    String aiHostName;

    private final FilmRepository filmRepository;

    private final FilmTypeRepository filmTypeRepository;

    private final PhotoRepository photoRepository;

    private final S3Service s3Service;

    private final PhotoBizService photoBizService;

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

    public BooleanDto startDeveloping(Long filmUid) throws JsonProcessingException{
        // default 3 day
        return startDeveloping(filmUid, 3);
    }

    public BooleanDto startDeveloping(Long filmUid, Integer day) throws JsonProcessingException{

        Film film = filmRepository.findById(filmUid).get();

        ObjectMapper json = new ObjectMapper();

        String jsonString;

        StartPrintingDto data = StartPrintingDto.builder()
                .film_code(film.getFilmType().getCode())
                .before_file_name("")
                .after_file_name("")
                .s3_file_path("")
                .build();


        for(Photo photo : photoRepository.findAllByFilm(film)){

            data.setBefore_file_name(photoBizService.getFileName(PHOTO_TYPE.ORG, photo.getUid()));
            data.setAfter_file_name(photoBizService.getFileName(PHOTO_TYPE.DEVELOPED, photo.getUid()));

            data.setS3_file_path(photoBizService.getS3Path(photo.getUid()));


            jsonString = json.writeValueAsString(data);

            if(HttpService.sendReq(aiHostName, jsonString)){
                continue;
            }else{
                return BooleanDto.builder().result(false).build();
            }
        }

        LocalDateTime now = LocalDateTime.now();
        film.setDevelopedStartAt(now);
        film.setDevelopedEndAt(now.plusDays(day));
        filmRepository.save(film);

        return BooleanDto.builder().result(true).build();

    }

    public BooleanDto delete(Long filmUid) throws IOException {

        try {
            Film film = filmRepository.findById(filmUid).orElseThrow(
                    () -> new EntityNotFoundException("No Film with " + filmUid.toString() + " Uid. Wrong FilmUid.")
            );

            film.setDeletedAt(LocalDateTime.now());

            filmRepository.save(film);

            return BooleanDto.success();


        } catch (Exception e) {
            return BooleanDto.fail(e.getMessage());
        }
    }

    public void changePrintStartAt(Long filmUid, LocalDateTime dateTime){

        Film film = filmRepository.findById(filmUid).get();
        film.setDevelopedStartAt(dateTime);
        filmRepository.save(film);

    }
    public void changePrintEndAt(Long filmUid, LocalDateTime dateTime){

        Film film = filmRepository.findById(filmUid).get();
        film.setDevelopedEndAt(dateTime);
        filmRepository.save(film);

    }

    public Film getFilmByPhoto(Long photoUid){

        return photoRepository.findById(photoUid).get().getFilm();

    }

    public Film getFilm(Long filmUid){

        return filmRepository.findById(filmUid).orElseThrow(
                () -> new EntityNotFoundException("Invalid filmUid")
        );

    }
}
