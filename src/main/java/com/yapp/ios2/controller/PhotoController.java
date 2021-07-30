package com.yapp.ios2.controller;


import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photo/*")
public class PhotoController {
//
    @Autowired
    private PhotoService photoService;
    @Autowired
    private PhotoRepository photoRepository;
//
//
    @Autowired
    private UserService userService;
//
    @GetMapping("/")
    @ResponseBody
    public String home(@AuthenticationPrincipal UserDetails user){

        return "WELCOME, " + user.getUsername() +
                " HERE IS PHOTO HOME " +
                user.getAuthorities();
    }

    @GetMapping("/getPhotoInfosByFilm/{filmUid}")
    @ResponseBody
    public List<Photo> getPhotoInfosByFilm(@PathVariable("filmUid") Long filmUid){

        return photoService.getPhotosByFilm(filmUid);

    }


    @PostMapping(value = "/upload")
    @ResponseBody
    public Photo upload(@RequestParam(value="image") MultipartFile images, @RequestParam("filmUid") Long filmUid, @AuthenticationPrincipal User user) throws IOException {

        Photo photo = photoService.upload(images, filmUid);

        return photo;
    }

    @PostMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] download(@RequestBody PhotoDto.PhotoDownload photoDownload) throws IOException {

        byte[] photoBinary = photoService.download(photoDownload.getPhotoUid());

        return photoBinary;
    }

    @GetMapping(value = "/delete/{photoUid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public boolean photoUid(@PathVariable("photoUid") Long photoUid) throws IOException {

        photoService.delete(photoUid);

        return true;
    }


}
