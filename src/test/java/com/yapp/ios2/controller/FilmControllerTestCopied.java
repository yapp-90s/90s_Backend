package com.yapp.ios2.controller;

import com.yapp.ios2.testConfig.TestInit;
import com.yapp.ios2.vo.User;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FilmControllerTestCopied extends TestInit {


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
    public void start_printing() throws Exception {

        User user = userRepository.findUserByName("90s_tester").get();

        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());

        Long filmUid = filmRepository.findAllByUser(user).get(0).getUid();

        mockMvc.perform(
                get("/film/startPrinting/" + filmUid.toString())
                        .header("X-AUTH-TOKEN", jwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

}
