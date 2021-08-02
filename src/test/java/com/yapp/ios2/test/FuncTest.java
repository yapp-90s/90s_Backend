package com.yapp.ios2.test;

import com.yapp.ios2.config.FuncUtils;
import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.testConfig.TestInit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FuncTest extends TestInit {
    @Autowired
    FilmProps filmProps;

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
        FuncUtils.addPhotoInFilmByUser(photoService, filmService, user);
    }

    @Test
    public void create_film_test() throws Exception{
        FuncUtils.createDummyFilms(filmProps.getFilms(), filmRepository, filmService, user);
    }

}
