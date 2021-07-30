package com.yapp.ios2.testConfig;

import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.config.FuncUtils;
import com.yapp.ios2.repository.FilmRepository;
import com.yapp.ios2.repository.PhotoRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class TestInit {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    public UserService userService;

    @Autowired
    public PhotoService photoService;

    @Autowired
    public FilmService filmService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public FilmRepository filmRepository;

    @Autowired
    public PhotoRepository photoRepository;

    @Autowired
    public JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    public User testUser;

    public String jwt;

    public User user;

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

        user = FuncUtils.createTester(userRepository, passwordEncoder);

    }


//    @After
//    public void After(){
//
//        userRepository.delete(this.testUser);
//
//    }


}
