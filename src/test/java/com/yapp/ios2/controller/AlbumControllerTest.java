//package com.yapp.ios2.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yapp.ios2.TestConfig;
//import com.yapp.ios2.config.JwtFilter;
//import com.yapp.ios2.config.JwtProvider;
//import com.yapp.ios2.dto.*;
//import com.yapp.ios2.repository.UserRepository;
//import com.yapp.ios2.service.AlbumService;
//import com.yapp.ios2.service.UserService;
//import com.yapp.ios2.vo.Album;
//import com.yapp.ios2.vo.User;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.JUnitRestDocumentation;
//import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
//import org.springframework.restdocs.snippet.Attributes;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.yapp.ios2.controller.TestFunc.createAlbums;
//import static com.yapp.ios2.controller.TestFunc.deleteTester;
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestConfig.class)
//@ActiveProfiles("test")
//public class AlbumControllerTest{
//    @Rule
//    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    AlbumService albumService;
//
//    @Autowired
//    WebApplicationContext context;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    JwtProvider jwtProvider;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    MockMvc mockMvc;
//    RestDocumentationResultHandler document;
//
//    User testUser;
//
//    String jwt;
//
//    @Before
//    public void setUp() {
//        this.document = document(
//                "{class-name}/{method-name}",
//                preprocessResponse(prettyPrint())
//        );
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation)
//                        .uris().withScheme("https").withHost("90s.com").withPort(443))
//                .apply(springSecurity())
//                .alwaysDo(document)
//                .build();
//
//    }
//
//    @Test
//    public void create() throws Exception {
//
//        Map<String ,Object> json = new HashMap<String, Object>();
//
//        json.put("photoLimit", 5);
//        json.put("name", "NameOfAlbum");
//        json.put("coverUid", 1);
//        json.put("endDate", "2020.08.01");
//
//        jwt = TestFunc.createTester(userRepository, passwordEncoder ,jwtProvider);
//
//        ObjectMapper obm = new ObjectMapper();
//        String jsonString = obm.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//        mockMvc.perform(
//                post("/album/create")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonString)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk()
//                );
//
//        deleteTester(jwt, userService, userRepository, jwtProvider);
//
//    }
//
//    @Test
//    public void getAlbums() throws Exception {
//
//        User user = TestFunc.createTester(userRepository, passwordEncoder);
//
//        createAlbums(3, user, albumService);
//
//        String jwt = jwtProvider.createToken(user.getUid().toString(),user.getRoles());
//
//        ObjectMapper obm = new ObjectMapper();
//
//        mockMvc.perform(
//                get("/album/getAlbums")
//                        .header("X-AUTH-TOKEN", jwt)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk()
//                );
//
//        deleteTester(jwt, userService, userRepository, jwtProvider);
//
//    }
//
////    @GetMapping("/getAlbums")
////    @ResponseBody
////    public List<Album> getAlbums(@AuthenticationPrincipal UserDetails user){
////        List<Album> albums = albumService.getAlbumsByUser(userService.getUserByEmail(user.getUsername()));
////
////        albums.forEach(
////                album -> {
////                    albumService.completeChecker(album.getUid());
////                }
////        );
////
////        return albums;
////    }
//
//}
