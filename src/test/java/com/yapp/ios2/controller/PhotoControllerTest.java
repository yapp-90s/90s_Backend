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
    public void upload_photo() throws Exception {

        user = userService.getUserByPhone("010-0000-0000");

        MockMultipartFile multipartFile = new MockMultipartFile("testPic.jpeg", new FileInputStream(new File("src/test/java/com/yapp/ios2/data/testPicture.jpeg")));

        mockMvc.perform(
                fileUpload("/photo/upload")
                        .file("image", multipartFile.getBytes())
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .param("filmUid", filmRepository.findAllByUser(user).get(0).getUid().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParts(partWithName("image").description("업로드할 이미지 파일. jpeg 형식.")),
                        requestParameters(parameterWithName("filmUid").description("사진이 소속된 필름의 아이디값."))
                        )
                );
    }

    @Test
    public void download_photo() throws Exception {
        user = userService.getUserByPhone("010-0000-0000");
        List<Photo> photos = photoRepository.findAllByUser(user);

        mockMvc.perform(
                get("/photo/download/" + photos.get(0).getUid())
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
