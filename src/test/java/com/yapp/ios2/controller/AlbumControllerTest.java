package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.testConfig.TestConfig;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class AlbumControllerTest extends TestInit {

    @Test
    public void run() throws Exception{

    }

    @Test
    public void create_album() throws Exception {

        jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE2MTI1NzA3MjQsImV4cCI6MjI0MzI5MDcyNH0.UCZtpbxD_3-mUAAtZwphgRSw-ZT7-DIbN2VZFzR0FQo";

        AlbumDto.AlbumInfo albumInfo = AlbumDto.AlbumInfo.builder()
                .name("테스트앨범")
                .coverCode(1001)
                .layoutCode(2001)
                .build();

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(albumInfo);

        mockMvc.perform(
                post("/album/create")
                        .header("X-AUTH-TOKEN", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }

    @Test
    public void get_albums() throws Exception {

        jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE2MTI1NzA3MjQsImV4cCI6MjI0MzI5MDcyNH0.UCZtpbxD_3-mUAAtZwphgRSw-ZT7-DIbN2VZFzR0FQo";

        mockMvc.perform(
                get("/album/getAlbums")
                        .header("X-AUTH-TOKEN", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }

    @Test
    public void add_photo_in_album() throws Exception {

        jwt = user.getJWT();

        Album album = albumService.getAlbumsByUser(user).get(0);

        Photo photo = photoRepository.findAllByUser(user).get(0);

        AlbumDto.AddPhotoInAlbum addPhotoInAlbum = AlbumDto.AddPhotoInAlbum.builder()
                .albumUid(album.getUid())
                .photoUid(photo.getUid())
                .paperNum(1)
                .sequence(2)
                .build();

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(addPhotoInAlbum);


        mockMvc.perform(
                post("/album/addPhotoInAlbum")
                        .header("X-AUTH-TOKEN", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }

}
