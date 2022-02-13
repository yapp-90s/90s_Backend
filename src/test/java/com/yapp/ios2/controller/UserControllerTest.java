package com.yapp.ios2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.testConfig.TestConfig;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.*;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.config.FuncUtils;
import com.yapp.ios2.testConfig.TestInit;
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

import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestConfig.class)
//@ActiveProfiles("test")
public class UserControllerTest extends TestInit {

    @Test
    public void run() throws Exception{
        loginWithEmailAndPhoneNum();
        loginWithOnlyEmailButNoEmail();
        loginWithOnlyEmail();
    }

    @Test
    private void createDummyUser() throws Exception{



    }

    @Test
    public void login_ErrorCode_C001() throws Exception {
    // 모든 필드를 다 안주고 일부 필드만해서 줬을 경우
        LoginDto loginDto = new LoginDto();
        loginDto.setEmailKakao("tester@90s.com");

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);

        mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andDo(document.document(
                ));
    }

    @Test
    public void loginWithOnlyEmail() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmailKakao("tester@90s.com");
        loginDto.setEmailApple("");
        loginDto.setEmailGoogle("");
        loginDto.setPhoneNum("");

        User user = FuncUtils.createTester(userRepository, passwordEncoder);

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);

        mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                ));

        FuncUtils.deleteTester(userService, user);

    }

    @Test
    public void loginWithOnlyEmailButNoEmail() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmailKakao("noTester@90s.com");
        loginDto.setEmailApple("");
        loginDto.setEmailGoogle("");
        loginDto.setPhoneNum("");

        User user = FuncUtils.createTester(userRepository, passwordEncoder);

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);

        mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andDo(document.document())
        ;
    }

    @Test
    public void loginWithEmailAndPhoneNum() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmailKakao("tester0@90s.com");
        loginDto.setEmailApple("");
        loginDto.setEmailGoogle("");
        loginDto.setPhoneNum("010-0000-0000");

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(loginDto);

        mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document())
        ;
    }


    @Test
    public void check_phoneNum() throws Exception {

        SmsRequestDto smsRequestDto = new SmsRequestDto();
        smsRequestDto.setPhoneNumber("010-9523-3114");

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(smsRequestDto);

        mockMvc.perform(
                post("/user/checkPhoneNum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                )
                ;

    }
}