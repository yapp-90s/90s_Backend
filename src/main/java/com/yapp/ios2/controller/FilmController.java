package com.yapp.ios2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios2.dto.*;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/film/*")
public class FilmController {

    private final FilmService filmService;

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

    @GetMapping(value = "/startDeveloping/{filmUid}")
    @ResponseBody
    public BooleanDto startDeveloping(@AuthenticationPrincipal User user, @PathVariable("filmUid") Long filmUid) throws JsonProcessingException {

        BooleanDto result = filmService.startDeveloping(filmUid);

        return result;
    }

    @DeleteMapping("/delete/{filmUid}")
    public BooleanDto delete(@PathVariable("filmUid") Long filmUid) throws IOException{

        BooleanDto result = filmService.delete(filmUid);

        return result;
    }

}
