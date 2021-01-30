//package com.yapp.ios2.config;
//
//import com.amazonaws.util.IOUtils;
//import com.yapp.ios2.repository.AlbumRepository;
//import com.yapp.ios2.repository.CoverRepository;
//import com.yapp.ios2.repository.PhotoRepository;
//import com.yapp.ios2.repository.UserRepository;
//import com.yapp.ios2.service.AlbumService;
//import com.yapp.ios2.service.PhotoService;
//import com.yapp.ios2.vo.Album;
//import com.yapp.ios2.vo.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//@RequiredArgsConstructor
//@Component
//public class DefaultUserInitializer{
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    JwtProvider jwtProvider;
//    @Autowired
//    AlbumRepository albumRepository;
//    @Autowired
//    PhotoRepository photoRepository;
//    @Autowired
//    CoverRepository coverRepository;
//    @Autowired
//    AlbumService albumService;
//    @Autowired
//    PhotoService photoService;
//    private final PasswordEncoder passwordEncoder;
//
//    public void run(String... args) throws Exception {
//
//        System.out.println("CREATE DEFAULT USER");
//        User defaultUser = userRepository.findUserByPhone("010-0000-0000").orElse(
//                User.builder()
//                        .emailKakao("tryer@90s.com")
//                        .emailGoogle("tryer@90s.com")
//                        .emailApple("tryer@90s.com")
//                        .phoneNum("010-0000-0000")
//                        .name("90s_tryer")
//                        .roles(Collections.singletonList("ROLE_TRYER"))
//                        .build()
//        );
//        userRepository.save(defaultUser);
//        System.out.println("DEFAULT USER JWT TOKEN : " + jwtProvider.createToken(defaultUser.getUid().toString(), defaultUser.getRoles()));
////        DefaultUser Album&Photo Init
//        if(albumRepository.findByUser(defaultUser).isEmpty()){
//            List<String> names = Arrays.asList(
//                    "소듕한 내 일상들",
//                    "집사라서 행복해",
//                    "금강산도식후경",
//                    "내 이야기",
//                    "함께한 지 365일",
//                    "우리가 계절을 기억하는 법");
//            List<Integer> photos = Arrays.asList(
//                    5,
//                    6,
//                    6,
//                    6,
//                    5,
//                    6
//            );
//            List<Long> covers = Arrays.asList(
//                    1L,
//                    2L,
//                    3L,
//                    4L,
//                    6L,
//                    7L
//            );
//            Integer photoCnt = 1;
//            for(int i = 0; i < 6; i++){
//                Album newAlbum = albumService.create(
//                        names.get(i),
//                        photos.get(i),
//                        defaultUser.getUid(),
//                        covers.get(i),
//                        LocalDate.now()
//                );
//
//                for(int j = 0; j < photos.get(i); j++){
//                    ClassPathResource resource = new ClassPathResource(
//                            "static/" + String.valueOf(i+1) + "/" + String.valueOf(j+1) + ".png"
//                    );
//                    InputStream inputStream = resource.getInputStream();
//
//                    MultipartFile multipartFile = new MockMultipartFile("file",
//                            String.valueOf(j+1) + ".png", "text/plain", IOUtils.toByteArray(inputStream));
//                    MultipartFile[] multipartFiles = {multipartFile};
//                    photoService.upload(multipartFiles,newAlbum.getUid());
//                }
//            }
//        }
//
////        TESTER INITIALIZER
//
//        for(int i = 0; i < 31; i++){
//            String name = "tester" + i;
//
//            if(userRepository.findUserByPhone(String.format("010-0000-%04d",i)).isPresent()) continue;
//
//            User testUser = userRepository.findUserByPhone(String.format("010-0000-%04d",i)).orElse(
//                    User.builder()
//                            .emailKakao(name + "@90s.com")
//                            .emailApple(name + "@90s.com")
//                            .emailGoogle(name + "@90s.com")
//                            .name("90s_" + name)
//                            .password(passwordEncoder.encode("test"))
//                            .phoneNum(String.format("010-0000-%04d",i))
//                            .roles(Collections.singletonList("ROLE_TESTER"))
//                            .build()
//            );
//
//            userRepository.save(testUser);
//            System.out.println(name + " JWT TOKEN : " + jwtProvider.createToken(testUser.getUid().toString(), testUser.getRoles()));
//
//            for(int j = 0; j < 6; j++){
//                albumService.create(
//                        "Album" + String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)),
//                        7,
//                        testUser.getUid(),
//                        Long.valueOf(j+1),
//                        LocalDate.now().plusDays(100)
//                );
//            }
//        }
//
//    }
//}
