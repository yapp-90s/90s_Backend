package com.yapp.ios2.controller;

import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.dto.LoginDto;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.dto.ResponseDto;
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
    public Film create(@AuthenticationPrincipal User user, @Valid @RequestBody FilmDto filmDto) {

        Film film = filmService.createFilm(filmDto.getFilmCode(), filmDto.getName(), user);

        return film;

    }
    @PostMapping(value = "/upload")
    @ResponseBody
    public Photo upload(@RequestParam(value="image") MultipartFile images, @RequestParam("filmUid") Long filmUid, @AuthenticationPrincipal User user) throws IOException {

        Photo photo = filmService.upload(images, filmUid);

        return photo;
    }

    @PostMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] download(@RequestBody PhotoDto.PhotoDownload photoDownload) throws IOException {

        byte[] photoBinary = filmService.download(photoDownload.getPhotoUid());

        return photoBinary;
    }

}
