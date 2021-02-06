package com.yapp.ios2.controller;

import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.dto.LoginDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/film/*")
public class FilmController {

    @Autowired
    FilmService filmService;

    @PostMapping(value = "/create")
    @ResponseBody
    public Film create(@AuthenticationPrincipal User user, @Valid @RequestBody FilmDto filmDto) {

        Film film = filmService.createFilm(filmDto.getFilmCode(), filmDto.getName(), user);

        return film;

    }

}
