package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.testConfig.TestConfig;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.repository.PhotoRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class FilmControllerTest{

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    UserService userService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PhotoRepository photoRepository;


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
                .apply(
                        documentationConfiguration(this.restDocumentation)
                        .uris()
                                .withScheme("http")
                                .withHost("49.50.162.246")
                                .withPort(8080)
                )
                .apply(
                        documentationConfiguration(this.restDocumentation).operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .apply(springSecurity())
                .alwaysDo(document)
                .build();
    }

    @Test
    public void create_film() throws Exception {

        jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE2MTI1NzA3MjQsImV4cCI6MjI0MzI5MDcyNH0.UCZtpbxD_3-mUAAtZwphgRSw-ZT7-DIbN2VZFzR0FQo";

        FilmDto filmDto = FilmDto.builder()
                .name("Test Film")
                .filmCode(1001)
                .build();

        ObjectMapper json = new ObjectMapper();
        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(filmDto);

        mockMvc.perform(
                post("/film/create")
                        .header("X-AUTH-TOKEN", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                );

    }

    @Test
    public void get_Films() throws Exception {

        jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE2MTI1NzA3MjQsImV4cCI6MjI0MzI5MDcyNH0.UCZtpbxD_3-mUAAtZwphgRSw-ZT7-DIbN2VZFzR0FQo";


        mockMvc.perform(
                get("/film/getFilms")
                        .header("X-AUTH-TOKEN", jwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }


//    @Test
//    public void upload_photo() throws Exception {
//
//        jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE2MTI1NzA3MjQsImV4cCI6MjI0MzI5MDcyNH0.UCZtpbxD_3-mUAAtZwphgRSw-ZT7-DIbN2VZFzR0FQo";
//
//        MockMultipartFile multipartFile = new MockMultipartFile("testPic.jpeg", new FileInputStream(new File("src/test/java/com/yapp/ios2/data/testPicture.jpeg")));
//
//        mockMvc.perform(
//                fileUpload("/film/upload")
//                        .file("image", multipartFile.getBytes())
//                        .header("X-AUTH-TOKEN", jwt)
//                        .param("filmUid","10")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document.document(
//                        requestParts(partWithName("image").description("업로드할 이미지 파일. jpeg 형식.")),
//                        requestParameters(parameterWithName("filmUid").description("사진이 소속된 필름의 아이디값."))
//                        )
//                );
//    }
//
//    @Test
//    public void download_photo() throws Exception {
//
//        jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9UUllFUiJdLCJpYXQiOjE2MTI1NzA3MjQsImV4cCI6MjI0MzI5MDcyNH0.UCZtpbxD_3-mUAAtZwphgRSw-ZT7-DIbN2VZFzR0FQo";
//
//        List<Photo> photos = photoRepository.findAll();
//
//
//        PhotoDto.PhotoDownload photoDownload = PhotoDto.PhotoDownload.builder()
//                .photoUid(photos.get(photos.size()-1).getUid())
//                .build();
//
//        ObjectMapper json = new ObjectMapper();
//        String jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(photoDownload);
//
//        mockMvc.perform(
//                post("/film/download")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
//                .andExpect(status().isOk()
//                );
//    }

}
