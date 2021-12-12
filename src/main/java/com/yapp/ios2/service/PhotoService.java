package com.yapp.ios2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.dto.PhotoInAlbumDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService{

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoInAlbumRepository photoInAlbumRepository;
    @Autowired
    S3Service s3Service;


    public List<PhotoDto> getPhotosByFilm(Long filmUid){
        List<PhotoDto> photos = new ArrayList<>();
        photoRepository.findAllByFilm(
                filmRepository.findById(filmUid).orElseThrow(
                        () -> new EntityNotFoundException("Invalid FilmUid")
                )
        ).forEach(
                photo -> {
                    photos.add(new PhotoDto(photo));
                }
        );

        return photos;
    }

    public List<PhotoDto> getPhotosByAlbum(Long albumUid){
        List<PhotoDto> photos = new ArrayList<>();

        photoInAlbumRepository.findAllByAlbum(albumRepository.findById(albumUid).get()).forEach(
                photoInAlbum -> {
                    photos.add(new PhotoDto(photoInAlbum.getPhoto()));
                }
        );

        return photos;
    }

    public List<PhotoInAlbumDto> getPhotoInfosByAlbum(Long albumUid){
        List<PhotoInAlbumDto> photoInfos = new ArrayList<>();

        photoInAlbumRepository.findAllByAlbum(albumRepository.findById(albumUid).get()).forEach(
                photoInAlbum -> {
                    photoInfos.add(new PhotoInAlbumDto(photoInAlbum));
                }
        );

        return photoInfos;
    }

    public List<PhotoDto> getPhotosByUser(Long userUid){
        List<PhotoDto> photos = new ArrayList<>();

        photoRepository.findAllByUser(userRepository.findById(userUid).get()).forEach(
                photo -> {
                    photos.add(new PhotoDto(photo));
                }
        );

        return photos;
    }

    public List<PhotoDto> getPrintedPhotos(User user){
        List<PhotoDto> photos = new ArrayList<>();

        filmRepository.findAllPrintedFilms().forEach(
                film -> {
                    photos.addAll(getPhotosByFilm(film.getUid()));
                }
        );

        return photos;

    }

    public PhotoDto upload(MultipartFile photo, Long filmUid) throws IOException {

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

        return new PhotoDto(newPhoto);
    }

    public byte[] download(Long photoUid) throws IOException {


        Photo photo = photoRepository.findById(photoUid)
                .orElseThrow(
                        () -> new EntityNotFoundException("Invalid Uid")
                );

        byte[] photoBinary = s3Service.download(photo.getFilm().getUid(), photo.getUid().toString());

        return photoBinary;
    }

    public void delete(Long photoUid) throws IOException {

        Photo photo = photoRepository.findById(photoUid).get();

        s3Service.delete(photo.getFilm().getUid(), photoUid);

        photoRepository.delete(photo);

    }

    public void delete(Film film) throws IOException {

        List<Photo> photos = photoRepository.findAllByFilm(film);

        for (Photo photo : photos) {
            s3Service.delete(film.getUid(), photo.getUid());
        }

        photoRepository.deleteAll(photos);

    }

////    정민이 API 호출
//    public void sendOtherApi(Object sendObj) {
//        try { // POST 메소드 URL 생성 & header setting
//            HttpClient client = HttpClientBuilder.create().build();
//            HttpPost postRequest = new HttpPost("http://localhost:8080/test/curlConnect");
//            postRequest.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
//            postRequest.setHeader("Connection", "keep-alive");
//            postRequest.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//            // post body setting
//             ObjectMapper mapper = new ObjectMapper();
//             postRequest.setEntity(new StringEntity(mapper.writeValueAsString(sendObj)));
//            // CURL execute
//            client.execute(postRequest);
//        } catch (Exception e){
////              log.error(ExceptionUtils.getStackTrace(e)); }
//        }
//    }


}
