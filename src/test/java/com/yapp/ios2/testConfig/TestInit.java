package com.yapp.ios2.testConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.config.FuncUtils;
import com.yapp.ios2.config.init.properties.CoverProps;
import com.yapp.ios2.config.init.properties.FilmProps;
import com.yapp.ios2.config.init.properties.LayoutProps;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.FilmDto;
import com.yapp.ios2.dto.PhotoDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.FilmService;
import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.AlbumLayout;
import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.FilmType;
import com.yapp.ios2.vo.User;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.List;

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
    protected UserService userService;

    @Autowired
    protected PhotoService photoService;

    @Autowired
    protected FilmService filmService;

    @Autowired
    protected AlbumService albumService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected FilmRepository filmRepository;

    @Autowired
    protected PhotoRepository photoRepository;

    @Autowired
    protected AlbumRepository albumRepository;

    @Autowired
    protected FilmTypeRepository filmTypeRepository;
    @Autowired
    protected AlbumLayoutRepository albumLayoutRepository;
    @Autowired
    protected AlbumCoverRepository albumCoverRepository;


    @Autowired
    protected FilmProps filmProps;

    @Autowired
    protected CoverProps coverProps;

    @Autowired
    protected LayoutProps layoutProps;

    @Autowired
    protected JwtProvider jwtProvider;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    protected User testUser;

    protected String jwt;

    protected User user;

    @Before
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation)
                        .uris().withScheme("http").withHost("133.186.220.56").withPort(80))
                .apply(springSecurity())
                .alwaysDo(document)
                .build();
        user = FuncUtils.createTester(userRepository, passwordEncoder);
    }


    public void createDummyFilms(User tester){
        for(FilmType filmType : filmProps.getFilms()){
            String name = String.format("dummyFilm%03d_", filmType.getCode()) + tester.getName();
            if(filmRepository.findAllByName(name).isEmpty()){
                filmService.createFilm(filmType.getCode(), name, tester);
            }
        }
    }

    public void addPhotoInFilmByUser(User user){
        List<FilmDto> films = filmService.getFilms(user);
        for(FilmDto film : films){
            for(int i = 1; i < 4; i++){
                String fileName = String.format("%d.jpeg",i);

                ClassPathResource resource = new ClassPathResource("pic/"+fileName);

                try{
                    MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(resource.getFile()));
                    photoService.uploadOrgPhoto(multipartFile, film.getFilmUid());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void startDevelopingFilm(Film film) throws JsonProcessingException {

        filmService.startDeveloping(film.getUid(), 0);

    }

    public void createDummyAlbums(AlbumRepository albumRepository, AlbumService albumService, User tester){
        for(int i = 1; i <= 4; i++){
            String name = String.format("dummyAlbum%02d_",i)+tester.getName();
            if(albumRepository.findAllByName(name).isEmpty()){
                AlbumDto album = albumService.create(
                        tester,
                        name,
                        1000 + i,
                        1000 + i
                );

                if( i%2 == 0){
                    albumService.complete(album.getAlbumUid());
                }

            }
        }
    }

    public void addPhotoInAlbumByUser(PhotoService photoService, FilmService filmService, AlbumService albumService, AlbumLayoutRepository albumLayoutRepository, User user){
        List<AlbumDto> albums = albumService.getAlbumsByUser(user);

        List<PhotoDto> photos = photoService.getPhotosByUser(user.getUid());
        Integer photoIdx = 0;
        Integer photoMax = photoService.getPhotosByUser(user.getUid()).size();


        for(int i = 1; i < albums.size(); i = i + 2){

            AlbumLayout albumLayout = albumLayoutRepository.findAlbumLayoutByCode(albums.get(i).getLayoutCode()).get();

            for(int j = 1; j < albumLayout.getTotPaper(); j *= 3){

                for( int k = 1; k < albumLayout.getPhotoPerPaper(); k++){

                    // film PrintEnd 설정
                    try{

                        Film film = filmService.getFilmByPhoto(photos.get(photoIdx++).getPhotoUid());

                        if (ObjectUtils.isEmpty(film.getDevelopedEndAt())){

                            filmService.changePrintStartAt(
                                    film.getUid(),
                                    LocalDateTime.now()
                            );

                            filmService.changePrintEndAt(
                                    film.getUid(),
                                    LocalDateTime.now()
                            );

                        }

                        albumService.addPhotoInAlbum(
                                albums.get(i).getAlbumUid(),
                                photos.get(photoIdx++).getPhotoUid(),
                                j,  // paperNum
                                k   // sequence
                        );

                    } catch (Exception e){
                        continue;
                    }

                }
            }
        }
    }

    protected User getTester(){
        return this.user;
    }

}
