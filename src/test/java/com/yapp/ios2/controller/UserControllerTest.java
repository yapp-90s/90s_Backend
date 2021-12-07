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


//    @Test
//    public void delete_account() throws Exception {
//
//        jwt = TestFunc.createTester(userRepository, passwordEncoder ,jwtProvider);
//
//        mockMvc.perform(
//                get("/user/delete")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void check_email() throws Exception {
////        jwt = TestFunc.createTester(userRepository, passwordEncoder ,jwtProvider);
//
//        DuplicatedEmailDto duplicatedEmailDto = new DuplicatedEmailDto();
//        duplicatedEmailDto.setEmail("tester@90s.com");
//
//        ObjectMapper json = new ObjectMapper();
//        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(duplicatedEmailDto);
//
//        mockMvc.perform(
//                post("/user/checkEmail")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document.document(
//                        requestFields(
//                                fieldWithPath("email").description("이메일").attributes(new Attributes.Attribute("format","test@90s.com"))
//                        )
//                ));
//
//    }
//
//
//    @Test
//    public void updatePhoneNumber() throws Exception {
//
//        jwt = TestFunc.createTester(userRepository, passwordEncoder ,jwtProvider);
//
//        UserDto.PhoneNum phoneNum = new UserDto.PhoneNum();
//        phoneNum.setPhoneNum("01095233114");
//
//        ObjectMapper json = new ObjectMapper();
//        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(phoneNum);
//
//        mockMvc.perform(
//                post("/user/updatePhoneNumber")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document.document(
//                        requestHeaders(headerWithName("X-AUTH-TOKEN").description("JWT"))
//                        ,requestFields(
//                                fieldWithPath("phoneNum").description("핸드폰번호").attributes(new Attributes.Attribute("format","01012345678"))
//                        )
//                ));
//
//        deleteTester(jwt, userService, userRepository, jwtProvider);
//
//    }
//
//
//    @Test
//    public void getUserProile() throws Exception {
//        jwt = TestFunc.createTester(userRepository, passwordEncoder ,jwtProvider);
//
//        mockMvc.perform(
//                get("/user/getUserProfile")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document.document(
//                        requestHeaders(headerWithName("X-AUTH-TOKEN").description("JWT"))
//                ));
//        deleteTester(jwt, userService, userRepository, jwtProvider);
//    }

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