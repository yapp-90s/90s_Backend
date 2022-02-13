package com.yapp.ios2.service;

import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.config.info.PHOTO_TYPE;
import com.yapp.ios2.dto.IDto;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.dto.PhotoInAlbumDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService extends PhotoBizService{

    private final FilmRepository filmRepository;

    private final PhotoRepository photoRepository;

    private final UserRepository userRepository;

    private final AlbumRepository albumRepository;

    private final PhotoInAlbumRepository photoInAlbumRepository;

    private final S3Service s3Service;

    public List<PhotoDto> getPhotosByFilm(Long filmUid){
        List<PhotoDto> photos = new ArrayList<>();

        photoRepository.findAllByFilm(
                filmRepository.findById(filmUid).orElseThrow(
                        () -> new EntityNotFoundException("NOT FOUND FILM_UID")
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

    public List<PhotoDto> getDevelopedPhotos(User user){
        List<PhotoDto> photos = new ArrayList<>();

        filmRepository.findAllDevelopedFilms().forEach(
                film -> {
                    photos.addAll(getPhotosByFilm(film.getUid()));
                }
        );

        return photos;

    }

    public IDto uploadOrgPhoto(MultipartFile photo, Long filmUid) throws IOException {
        return this.upload(photo, null, filmUid, null,PHOTO_TYPE.ORG);
    }

    public IDto uploadDecoratedPhoto(MultipartFile photo, Long photoUid, Long albumUid) throws IOException{
        return this.upload(photo, photoUid, null, albumUid, PHOTO_TYPE.ORG);
    }

    public IDto upload(MultipartFile file,
                       Long photoUid ,Long filmUid, Long albumUid ,
                       PHOTO_TYPE type) throws IOException {
        Photo photo = new Photo();

        if(ObjectUtils.isEmpty(photoUid)){
            // CASE 1 : ADD NEW PHOTO
            photo  = Photo.builder()
                    .film(filmRepository.findById(filmUid).get())
                    .build();


        }else if (!ObjectUtils.isEmpty(photoUid) && !ObjectUtils.isEmpty(albumUid)){
            // CASE 2 : ADD DECORATED PHOTO ( PHOTO_UID AND ALBUM_UID IS NEEDED)
            photo = photoRepository.findById(photoUid).get();
            photo.setAlbum(albumRepository.findById(albumUid).get());

        }

        photoRepository.save(photo);

        String filePath = super.getS3FilePath(type, photo.getUid());

        try{
            s3Service.upload(file, filePath);
        }catch (Exception e){

        }

        return new PhotoDto(photo);
    }

    public byte[] download(Long photoUid, PHOTO_TYPE photoType) throws IOException {


        Photo photo = photoRepository.findById(photoUid)
                .orElseThrow(
                        () -> new EntityNotFoundException("Invalid Uid")
                );

        byte[] photoBinary = s3Service.download(photoUid.toString(), getFileName(photoType, photoUid));

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
