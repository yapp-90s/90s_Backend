package com.yapp.ios2.controller;


import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo/*")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoRepository photoRepository;
    private final UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String home(@AuthenticationPrincipal UserDetails user){

        return "WELCOME, " + user.getUsername() +
                " HERE IS PHOTO HOME " +
                user.getAuthorities();
    }

    @GetMapping("/getPhotoInfosByFilm/{filmUid}")
    @ResponseBody
    public List<PhotoDto> getPhotoInfosByFilm(@PathVariable("filmUid") Long filmUid){

        return photoService.getPhotosByFilm(filmUid);

    }
    @GetMapping("/getPrintedPhotoInfos")
    @ResponseBody
    public List<PhotoDto> getPrintedPhotoInfosByFilm(@AuthenticationPrincipal User user){

        return photoService.getPrintedPhotos(user);

    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public PhotoDto upload(@RequestParam(value="image") MultipartFile images, @RequestParam("filmUid") Long filmUid, @AuthenticationPrincipal User user) throws IOException {

        PhotoDto photo = photoService.upload(images, filmUid);

        return photo;
    }

    @GetMapping(value = "/download/{photoUid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] download(@PathVariable(value="photoUid") Long photoUid) throws IOException {

        byte[] photoBinary = photoService.download(photoUid);

        return photoBinary;
    }

    @GetMapping(value = "/delete/{photoUid}")
//    @ResponseBody()
    public boolean delete(@PathVariable("photoUid") Long photoUid) throws IOException {

        photoService.delete(photoUid);

        return true;
    }


}
