package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.testConfig.TestConfig;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PhotoControllerTest extends TestInit {

    @Test
    public void upload_org_photo() throws Exception {

        user = getTester();

        MockMultipartFile multipartFile = new MockMultipartFile("testPic.png", new FileInputStream(new File("src/test/java/com/yapp/ios2/data/testPic.png")));


        List<Film> films = filmRepository.findByDevelopedEndAtIsNull();
        Long filmUid = 0l;

        if(films.isEmpty()){
            filmUid = filmService.createFilm(1001, LocalDateTime.now().toString(), user).getFilmUid();
        }else{
            filmUid = films.get(0).getUid();
        }

        mockMvc.perform(
                fileUpload("/photo/upload/org/")
                        .file("image", multipartFile.getBytes())
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .param("filmUid", filmUid.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParts(partWithName("image").description("업로드할 이미지 파일. png 형식.")),
                        requestParameters(parameterWithName("filmUid").description("사진이 소속된 필름의 아이디값."))
                        )
                );
    }

    @Test
    public void upload_decorated_photo() throws Exception {

        user = getTester();

        MockMultipartFile multipartFile = new MockMultipartFile("testPic.png", new FileInputStream(new File("src/test/java/com/yapp/ios2/data/testPic.png")));

        Photo photo = photoRepository.findAllByUser(user).get(0);

        Album album = albumRepository.findByUser(user).get(0);
        mockMvc.perform(
                fileUpload("/photo/upload/decorated/")
                        .file("image", multipartFile.getBytes())
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .param("photoUid", photo.getUid().toString())
                        .param("albumUid", album.getUid().toString())
                        .param("paperNum", String.valueOf(1))
                        .param("sequence", String.valueOf(1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParts(partWithName("image").description("업로드할 이미지 파일. png 형식.")),
                        requestParameters(
                                parameterWithName("photoUid").description("사진의 UID"),
                                parameterWithName("albumUid").description("앨범의 UID"),
                                parameterWithName("paperNum").description("앨범 내 사진 위치(페이지)"),
                                parameterWithName("sequence").description("앨범 내 사진 위치(순서)"))
                        )
                );
    }


    @Test
    public void download_org_photo() throws Exception {
        user = getTester();
        List<Photo> photos = photoRepository.findAllByUser(user);

        mockMvc.perform(
                get("/photo/download/org/" + photos.get(0).getUid())
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void getPhotoInfosByFilm() throws Exception{
        user = userService.getUserByPhone("010-0000-0000");
        Long filmUid = filmRepository.findAllByUser(user).get(0).getUid();

        mockMvc.perform(
                get("/photo/getPhotoInfosByFilm/" + filmUid)
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void delete_photo() throws Exception{
        user = userService.getUserByPhone("010-0000-0000");
        Long photoUid = photoRepository.findAllByFilm(filmRepository.findAllByUser(user).get(0)).get(0).getUid();

        mockMvc.perform(
                get("/photo/delete/" + photoUid)
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void getPrintedPhotoInfos() throws Exception{
        user = userService.getUserByPhone("010-0000-0000");

        mockMvc.perform(
                get("/photo/getPrintedPhotoInfos/")
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }


}
