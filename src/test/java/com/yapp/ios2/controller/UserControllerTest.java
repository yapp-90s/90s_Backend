package com.yapp.ios2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.TestConfig;
import com.yapp.ios2.config.JwtFilter;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.*;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.UserService;
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
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import static com.yapp.ios2.controller.TestFunc.deleteTester;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class UserControllerTest{
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    UserService userService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    MockMvc mockMvc;
    RestDocumentationResultHandler document;

    User testUser;

    String jwt;

    @Before
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation)
                        .uris().withScheme("https").withHost("90s.com").withPort(443))
                .apply(springSecurity())
                .alwaysDo(document)
                .build();

    }

//    @Test
//    public void join() throws Exception {
//
//        JoinDto joinDto = new JoinDto();
//        joinDto.setEmailKakao("tester@90s.com");
//        joinDto.setName("tester");
//        joinDto.setPhone("010-9523-3114");
//
//        ObjectMapper json = new ObjectMapper();
//        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(joinDto);
//
//        mockMvc.perform(
//                post("/user/join")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        deleteTester(userService, userRepository, jwtProvider);

//                .andDo(document.document(
//                        requestFields(
//                                fieldWithPath("email").description("이메일").attributes(new Attributes.Attribute("format", "test@90s.com")),
//                                fieldWithPath("name").description("유저 이름"),
//                                fieldWithPath("password").description("비밀번호").attributes(new Attributes.Attribute("format", "카카오 로그인 시에는 null로 보내지 않아도 무관합니다.")),
//                                fieldWithPath("phone").type("String").description("핸드폰 번호").attributes(new Attributes.Attribute("format", "010-1234-5678")),
//                                fieldWithPath("sosial").type("Boolean").description("카카오 로그인 여부").attributes(new Attributes.Attribute("format", "true / false"))
//                        )
//                ));

//        if(userRepository.findByEmail("tester@90s.com").isPresent()){
//            userRepository.delete(userRepository.findByEmail("tester@90s.com").get());
//        }
//    }

    @Test
    public void login_ErrorCode_C001() throws Exception {

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
                .andExpect(status().isOk())
                .andDo(document.document(
                ));
    }

    @Test
    public void login() throws Exception {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmailKakao("tester@90s.com");
        loginDto.setEmailApple("");
        loginDto.setEmailGoogle("");

        User user = TestFunc.createTester(userRepository, passwordEncoder);

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

        TestFunc.deleteTester(userService, user);

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

//    @Test
//    public void check_phoneNum() throws Exception {
//
//        SmsDto.SmsRequestDto smsRequestDto = new SmsDto.SmsRequestDto();
//        smsRequestDto.setPhoneNumber("01095233114");
//
//        ObjectMapper json = new ObjectMapper();
//        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(smsRequestDto);
//
//        mockMvc.perform(
//                post("/user/checkPhoneNum")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document.document(
//                        requestFields(
//                                fieldWithPath("phoneNumber").description("핸드폰번호").attributes(new Attributes.Attribute("format","01012341234")).description("핸드폰 번호를 보내며 - 없이 숫자만 보냅니다.")
//                        )
//                ));
//
//    }
}