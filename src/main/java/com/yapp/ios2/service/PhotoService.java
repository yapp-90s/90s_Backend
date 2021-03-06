package com.yapp.ios2.service;

import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.vo.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService{

    @Autowired
    FilmRepository filmRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    S3Service s3Service;

    public Photo upload(MultipartFile photo, Long filmUid) throws IOException {



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

        return newPhoto;
    }

    public byte[] download(Long photoUid) throws IOException {


        Photo photo = photoRepository.findById(photoUid)
                .orElseThrow(
                        () -> new EntityNotFoundException("Invalid Uid")
                );

        byte[] photoBinary = s3Service.download(photo.getFilm().getUid(), photo.getUid().toString());

        return photoBinary;
    }

}
