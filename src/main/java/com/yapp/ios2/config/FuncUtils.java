package com.yapp.ios2.config;

import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.FilmType;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FuncUtils {

    public static User createTester(UserRepository userRepository, PasswordEncoder passwordEncoder){
        User testUser = userRepository.findUserByPhone("010-1234-1234").orElse(
                User.builder()
                        .emailKakao("tester@90s.com")
                        .emailGoogle("tester@90s.com")
                        .emailApple("tester@90s.com")
                        .name("90s_tester")
                        .phoneNum("010-1234-1234")
                        .roles(Collections.singletonList("ROLE_TESTER"))
                        .build()
        );
        userRepository.save(testUser);
        return testUser;
    }

    public static void deleteTester(UserService userService, User user){
        userService.delete(user);
    }

    public static void createDummyFilms(List<FilmType> filmTypes, FilmRepository filmRepository, FilmService filmService, User tester){
        for(FilmType filmType : filmTypes){
            String name = String.format("dummyFilm%04d_", filmType.getCode()) + tester.getName();
            if(filmRepository.findAllByName(name).isEmpty()){
                filmService.createFilm(filmType.getCode(), name, tester);
            }
        }
    }

    public static void createDummyAlbums(AlbumRepository albumRepository, AlbumService albumService, User tester){
        for(int i = 1; i <= 4; i++){
            String name = String.format("dummyAlbum%02d_",i)+tester.getName();
            if(albumRepository.findAllByName(name).isEmpty()){
                albumService.create(
                        tester,
                        name,
                        1000 + i,
                        1000 + i
                        );
             }
        }
    }

    public static void addPhotoInFilmByUser(PhotoService photoService, FilmService filmService, User user){
        List<FilmDto> films = filmService.getFilms(user);


        for(FilmDto film : films){
            for(int i = 1; i < 4; i++){
                String fileName = String.format("%d.jpeg",i);

                ClassPathResource resource = new ClassPathResource("pic/"+fileName);
                System.out.println(resource.exists());

                try{
                    MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(resource.getFile()));
                    photoService.upload(multipartFile, film.getFilmUid());
                }catch(Exception e){
                    System.out.println("addPhotoInFilmByUser");
                }

            }
        }

    }


    
}
