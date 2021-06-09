package com.yapp.ios2.test;

import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.testConfig.TestFunc;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FuncTest extends TestInit {

    @Test
    public void get_Films() throws Exception {

        jwt = user.getJWT();


        mockMvc.perform(
                get("/film/getFilms")
                        .header("X-AUTH-TOKEN", jwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void upload_test() throws Exception{
        TestFunc.addPhotoInFilmByUser(photoService, filmService, user);
    }

    @Test
    public void create_film_test() throws Exception{
        TestFunc.createDummyFilms(filmRepository, filmService, user);
    }

}
