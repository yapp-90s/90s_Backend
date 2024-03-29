package com.yapp.ios2.controller;

import com.yapp.ios2.dto.AddPhotoInAlbumInDto;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.CreateAlbumInDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.service.AlbumService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album/*")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        System.out.println("album home");
        return "Welcome, HERE IS ALBUM HOME";
    }

    @PostMapping("/create")
    @ResponseBody
    public AlbumDto createAlbum(@AuthenticationPrincipal User user, @RequestBody CreateAlbumInDto albumInfo){
//        create(User user, String name, Integer totPaper, Long coverUid, Long layoutUid)

        AlbumDto albumDto = albumService.create(
                user,
                albumInfo.getName(),
                albumInfo.getCoverCode(),
                albumInfo.getLayoutCode()
        );

        return albumDto;
    }


//    @PostMapping("/addUser")
//    public ResponseDto.BooleanDto addUser(@RequestBody AlbumOwnerDto.AlbumOwnerInfo albumOwnerInfo){
//        AlbumOwner albumOwner = albumService.addOwner(albumOwnerInfo.getAlbumUid(), albumOwnerInfo.getUserUid(), albumOwnerInfo.getRole());
//        ResponseDto.BooleanDto result = new ResponseDto.BooleanDto();
//        if(albumOwner != null){
//            result.setResult(true);
//        }else{
//            result.setResult(false);
//        }
//        return result;
//    }
//
//    @PostMapping("/removeUser")
//    public ResponseDto.BooleanDto removeUser(@RequestBody AlbumOwnerDto.AlbumOwnerInfo albumOwnerInfo){
//        albumService.removeOwner(
//                albumOwnerInfo.getAlbumUid(),
//                albumOwnerInfo.getUserUid()
//        );
//        ResponseDto.BooleanDto result = new ResponseDto.BooleanDto();
//        result.setResult(true);
//
//        return result;
//    }
//
//
//
    @GetMapping("/getAlbums")
    @ResponseBody
    public List<AlbumDto> getAlbums(@AuthenticationPrincipal User user){

        List<AlbumDto> albums = albumService.getAlbumsByUser(user);

        return albums;
    }

    @PostMapping("/addPhotoInAlbum")
    public ResponseDto.BooleanDto addPhotoInAlbum(@RequestBody AddPhotoInAlbumInDto addPhotoInAlbum){
        ResponseDto.BooleanDto result = albumService.addPhotoInAlbum(
                addPhotoInAlbum.getAlbumUid(), //albumUid
                addPhotoInAlbum.getPhotoUid(), //photoUid
                addPhotoInAlbum.getPaperNum(), //paperNum
                addPhotoInAlbum.getSequence()  //sequence
        );

        return result;
    }

    // 앨범 완성 상태 바꾸기
    @GetMapping("/complete/{albumUid}")
    @ResponseBody
    public ResponseDto.BooleanDto complete(@PathVariable("albumUid")Long albumUid){

         ResponseDto.BooleanDto result = albumService.complete(albumUid);
//        albums.forEach(
//                album -> {
//                    albumService.completeChecker(album.getUid());
//                }
//        );

        return result;
    }

    @DeleteMapping("/delete/{albumUid}")
    public ResponseDto.BooleanDto delete(@PathVariable("albumUid") Long albumUid){

        ResponseDto.BooleanDto result = albumService.delete(albumUid);

        return result;
    }

    //
//    @GetMapping("/getAlbumPassword/{albumUid}")
//    public String getAlbumPassword(@PathVariable("albumUid") Long albumUid){
//
//        Album album = albumService.getAlbum(albumUid);
//
//        return album.getPassword();
//
//    }
//
//    @PostMapping("/joinAlbumByPassword")
//    public AlbumOwner joinAlbumByPassword(@AuthenticationPrincipal User user, @RequestBody AlbumDto.AlbumPassword albumPassword){
//        return albumService.joinAlbumByPassword(albumPassword.getPassword(), user);
//    }
//
//    @GetMapping("/updateAlbumPassword/{albumUid}")
//    public String updateAlbumPassword(@PathVariable("albumUid") Long albumUid){
//
//        Album album = albumService.getAlbum(albumUid);
//
//        album.setPassword(UUID.randomUUID().toString());
//
//        albumRepository.save(album);
//
//        return album.getPassword();
//
//    }
//
//    @PostMapping(value = "/getAlbumCover", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public HttpEntity<byte[]> getAlbumCover(@RequestBody AlbumDto.AlbumUid albumUid,
//                                            HttpServletResponse response) throws IOException {
//        Album album =albumService.getAlbum(albumUid.getUid());
//
//        ClassPathResource resource = new ClassPathResource(
//                album.getAlbumType().getPath()
//        );
//
//        byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        response.setHeader("Content-Disposition", "attachment; filename=" + album.getName() + "_cover.jpeg");
//
//        return new HttpEntity(bytes, headers);
//    }
//
//
//    @PostMapping("/getAlbumOwners")
//    public List<AlbumOwnerDto.AlbumOwnerInfo> getAlbumOwners(@AuthenticationPrincipal User user, @RequestBody AlbumDto.AlbumUid albumUid){
//        List<AlbumOwnerDto.AlbumOwnerInfo> albumOwners = albumService.getAlbumOwners(albumUid.getUid());
//
//        for(AlbumOwnerDto.AlbumOwnerInfo albumOwnerInfo : albumOwners){
//            if(albumOwnerInfo.getUserUid().intValue() != user.getUid().intValue()){
//                albumOwnerInfo.setUserUid(null);
//            }
//        }
//
//        return albumOwners;
//    }
//
//    @GetMapping("/plusCount/{albumUid}")
//    @Secured({"TESTER", "USER"})
//    public void plusCount(@PathVariable("albumUid") Long albumUid){
//
//        albumService.plusCount(albumUid);
//
//    }
//
}
