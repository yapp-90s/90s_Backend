package com.yapp.ios2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios2.dto.*;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/film/*")
public class FilmController {

    @Autowired
    FilmService filmService;

    @PostMapping(value = "/create")
    @ResponseBody
    public FilmDto create(@AuthenticationPrincipal User user, @Valid @RequestBody CreateFilmDto createFilmDto) {

        FilmDto film = filmService.createFilm(createFilmDto.getFilmCode(), createFilmDto.getName(), user);

        return film;
    }

    @GetMapping(value = "/getFilms")
    @ResponseBody
    public List<FilmDto> getFilms(@AuthenticationPrincipal User user) {

        List<FilmDto> films = filmService.getFilms(user);

        return films;
    }

    @GetMapping(value = "/startPrinting/{filmUid}")
    @ResponseBody
    public ResponseDto.BooleanDto startPrinting(@AuthenticationPrincipal User user, @PathVariable("filmUid") Long filmUid) throws JsonProcessingException {

        ResponseDto.BooleanDto result = filmService.startPrinting(filmUid);

        return result;
    }

    @DeleteMapping("/delete/{filmUid}")
    public ResponseDto.BooleanDto delete(@PathVariable("filmUid") Long filmUid) throws IOException{

        ResponseDto.BooleanDto result = filmService.delete(filmUid);

        return result;
    }

}
