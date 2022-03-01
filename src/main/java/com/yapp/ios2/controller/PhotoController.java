package com.yapp.ios2.controller;


import com.yapp.ios2.config.info.PHOTO_TYPE;
import com.yapp.ios2.dto.BooleanDto;
import com.yapp.ios2.dto.IDto;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.dto.PhotoInAlbumDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
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
    private final AlbumService albumService;

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
    @GetMapping("/getDevelopedPhotoInfos")
    @ResponseBody
    public List<PhotoDto> getDevelopedPhotoInfosByFilm(@AuthenticationPrincipal User user){

        return photoService.getDevelopedPhotos(user);

    }

    @PostMapping(value = "/upload/org/")
    @ResponseBody
    public PhotoDto uploadOrgPhoto(@RequestParam(value="image") MultipartFile images, @RequestParam("filmUid") Long filmUid, @AuthenticationPrincipal User user) throws IOException {

        PhotoDto photo = (PhotoDto)photoService.uploadOrgPhoto(images, filmUid);

        return photo;
    }

    @PostMapping(value = "/upload/decorated/")
    @ResponseBody
    public IDto uploadDecoratedPhoto(@RequestParam(value="image") MultipartFile images,
                                     @RequestParam("photoUid") Long photoUid,
                                     @RequestParam("albumUid") Long albumUid,
                                     @RequestParam("paperNum") Integer paperNum,
                                     @RequestParam("sequence") Integer sequence,
                                     @AuthenticationPrincipal User user) throws IOException {

        PhotoInAlbumDto photoInAlbumDto = new PhotoInAlbumDto();

        PhotoDto photo = (PhotoDto)photoService.uploadDecoratedPhoto(images, photoUid);

        BooleanDto booleanDto = albumService.addPhotoInAlbum(albumUid, photoUid, paperNum, sequence);

        if(booleanDto.getResult()){
            // Success
            photoInAlbumDto.setPhotoUid(photo.getPhotoUid());
            photoInAlbumDto.setAlbumUid((photo.getAlbumUid()));
            photoInAlbumDto.setPaperNum(paperNum);
            photoInAlbumDto.setSequence(sequence);
        }

        return photoInAlbumDto;
    }

    @GetMapping(value = "/download/org/{photoUid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] getOrgFile(@PathVariable(value="photoUid") Long photoUid) throws IOException {

        byte[] photoBinary = photoService.download(photoUid, PHOTO_TYPE.ORG);

        return photoBinary;
    }

    @GetMapping(value = "/download/developed/{photoUid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] getDevelopedFile(@PathVariable(value="photoUid") Long photoUid) throws IOException {

        byte[] photoBinary = photoService.download(photoUid, PHOTO_TYPE.DEVELOPED);

        return photoBinary;
    }

    @GetMapping(value = "/download/decorated/{photoUid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public byte[] getDecoratedFile(@PathVariable(value="photoUid") Long photoUid) throws IOException {

        byte[] photoBinary = photoService.download(photoUid, PHOTO_TYPE.DECORATED);

        return photoBinary;
    }

    @GetMapping(value = "/delete/{photoUid}")
//    @ResponseBody()
    public boolean delete(@PathVariable("photoUid") Long photoUid) throws IOException {

        photoService.delete(photoUid);

        return true;
    }


}
