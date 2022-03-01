package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.dto.CreateFilmDto;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.testConfig.TestConfig;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class FilmControllerTest extends TestInit {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void create_film() throws Exception {

        CreateFilmDto createFilmDto = CreateFilmDto.builder()
                .name("Test Film")
                .filmCode(1001)
                .build();

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(createFilmDto);

        System.out.println(jsonString);


        mockMvc.perform(
                post("/film/create")
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }


    @Test
    public void get_Films() throws Exception {

        user = userService.getUserByPhone("010-0000-0000");
        mockMvc.perform(
                get("/film/getFilms")
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void start_developing() throws Exception {
        user = getTester();
        Long filmUid = 65L;

        mockMvc.perform(
                get("/film/startDeveloping/" + filmUid.toString())
                        .header("X-AUTH-TOKEN", user.getJWT())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

        ;
    }

    @Test
    public void delete_film() throws Exception {

        user = userService.getUserByPhone("010-0000-0000");
        String jwt = user.getJWT();

        Film film = filmRepository.findAllByUser(user).get(0);

        mockMvc.perform(
                delete("/film/delete/" + film.getUid())
                        .header("X-AUTH-TOKEN", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );
    }


}
