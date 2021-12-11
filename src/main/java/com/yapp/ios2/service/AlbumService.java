package com.yapp.ios2.service;

import com.yapp.ios2.config.exception.AlbumNotFoundException;
import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.config.exception.UserNotFoundException;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.BooleanDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumOwnerRepository albumOwnerRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoInAlbumRepository photoInAlbumRepository;

    @Autowired
    S3Service s3Service;

    @Autowired
    AlbumCoverRepository albumCoverRepository;

    @Autowired
    AlbumLayoutRepository albumLayoutRepository;

    public AlbumDto create(User user, String name, Integer coverCode, Integer layoutCode) {

        AlbumDto albumDto;

        Album newAlbum = Album.builder()
                .name(name)
                .albumLayout(albumLayoutRepository.findAlbumLayoutByCode(coverCode).get())
                .albumCover(albumCoverRepository.findAlbumCoverByCode(layoutCode).get())
                .readCnt(0)
                .build();

        albumRepository.save(newAlbum);

        addOwner(newAlbum, user, "ROLE_CREATOR");

        albumDto = new AlbumDto(newAlbum);

        return albumDto;
    }

    public void addOwner(Album album, User user, String role){

        AlbumOwner albumOwner = AlbumOwner.builder()
                .album(album)
                .user(user)
                .role(role)
                .build();

        albumOwnerRepository.save(albumOwner);

    }

//    public void removeOwner(Long albumUid, Long user){
//
//        AlbumOwner albumOwner = albumOwnerRepository.findByAlbumAndUser(
//                albumRepository.findById(albumUid).get(),
//                userRepository.findById(user).get()
//        );
//
//        if(albumOwner.getRole().contains("CREATOR")){
//            if(albumOwnerRepository.findByAlbumUid(albumUid).size() == 1){
////                엘범소유주가 한명인 경우, 엘범을 삭제
//                albumOwnerRepository.delete( albumOwner );
//                albumRepository.delete(albumRepository.findById(albumUid).get());
//            }else{
//                albumOwnerRepository.delete( albumOwner );
////                Change new Creator
//                AlbumOwner newCreatorAlbumOwner = albumOwnerRepository.findByAlbumUid(albumUid).get(0);
//                newCreatorAlbumOwner.setRole("ROLE_CREATOR");
//                albumOwnerRepository.save(newCreatorAlbumOwner);
//            }
//        }else{
//            albumOwnerRepository.delete( albumOwner );
//        }
//
//    }
//
//    public AlbumOwner joinAlbumByPassword(String albumPassword, User user){
//        Album album = albumRepository.findByPassword(
//                albumPassword);
//
//        AlbumOwner albumOwner = AlbumOwner.builder()
//                .album(album)
//                .user(user)
//                .role("ROLE_GUEST")
//                .build();
//
//        albumOwnerRepository.save(
//                albumOwner
//        );
//
//        return albumOwner;
//    }
//
//    public void removeAlbum(Album album){
//        // Delete all photos in S3
//        s3Service.deleteByAlbum(album.getUid());
//        albumRepository.delete(album);
//    }
//
//
//    public Album getAlbum(Long albumUid) {
//
//        Album album = albumRepository.findById(albumUid)
//                .orElseThrow(() -> new IllegalArgumentException("Not exist album"));
//
//        return album;
//    }
//
    public List<AlbumDto> getAlbumsByUser(User user) {

        List<AlbumDto> albums = new ArrayList<AlbumDto>();

        albumRepository.findByUser(user).forEach(
                album -> {
                    albums.add(new AlbumDto(album));
                }
        );

        return albums;
    }

    public BooleanDto addPhotoInAlbum(Long albumUid, Long photoUid, Integer paperNum, Integer sequence) {

        BooleanDto result = BooleanDto.builder()
                .result(false)
                .msg("")
                .build();

        Photo photo = photoRepository.findById(photoUid).orElseThrow(
                () -> new EntityNotFoundException(String.format("Photo UID (%d) is not Exist.", photoUid.intValue()))
        );
        Album album = albumRepository.findById(albumUid).orElseThrow(
                () -> new EntityNotFoundException(String.format("Album UID (%d) is not Exist.", albumUid.intValue()))
        );

        // 유호성 검사

        if( !photoInAlbumRepository.findAllByPaperNumAndSequence(paperNum, sequence).isEmpty() ){
            // 저장하려는 사진 위치에 이미 사진이 있는지 여부.

            BooleanDto.builder()
                .msg("Photo is Changed.")
                .build();


        }else if ( album.getAlbumLayout().getTotPaper() < paperNum || album.getAlbumLayout().getPhotoPerPaper() < sequence ){
            // 저장하려는 위치가 정당한지 여부(전체 사진 페이지를 넘기지 않는지, 각 페이지별 들어가는 사진 순서에 해당하는 지)

            return BooleanDto.builder()
                    .result(false)
                    .msg("Invalid Value in PaperNum or Sequence.")
                    .build();
        }

        PhotoInAlbum photoInAlbum = PhotoInAlbum.builder()
                .photo(photo)
                .album(album)
                .paperNum(paperNum)
                .sequence(sequence)
                .build();

        photoInAlbumRepository.save(photoInAlbum);

        return BooleanDto.builder().result(true).build();
    }

//
//    public List<AlbumOwnerDto.AlbumOwnerInfo> getAlbumOwners(Long albumUid){
//
//        List<User> owners = userRepository.findUsersByAlbum(albumRepository.findById(albumUid).get());
//
//        List<AlbumOwner> albumOwners = albumOwnerRepository.findByAlbumUid(albumUid);
//
//        List<AlbumOwnerDto.AlbumOwnerInfo> albumOwnerInfos = AlbumOwnerDto.convertFromAlbumOwnerListToAlbumOwnerInfoList(albumOwners);
//
//        return albumOwnerInfos;
//
//    }
//
//
//
    // 엘범 읽기 횟수 증가
    public void plusReadCnt(Long albumUid){
        Album album = albumRepository.findById(albumUid).
                orElseThrow(() -> new AlbumNotFoundException());

        Integer count = album.getReadCnt();
        album.setReadCnt(++count);
        albumRepository.save(album);

    }
//
//
//    public void completeChecker(Long albumUid){
//        Album album = albumRepository.findById(albumUid).get();
//        if(!album.isComplete()){
//            if(photoRepository.findByAlbum(album).size() >= album.getPhotoLimit()){
//                album.setComplete(true);
//                albumRepository.save(album);
//            }else if(album.getEndDate().isBefore(ChronoLocalDate.from(LocalDate.now()))){
//                album.setComplete(true);
//                albumRepository.save(album);
//            }
//        }
//    }
//
    // 엘범 완성
    public BooleanDto complete(Long albumUid){

        Album album = albumRepository.findById(albumUid).orElseThrow(
                () -> new EntityNotFoundException("Album is not Existed.")
        );

        album.setCompletedAt(LocalDateTime.now());

        albumRepository.save(album);

        return BooleanDto.builder()
                .result(true)
                .build();
    }

    public BooleanDto delete(Long albumUid){

        try{
            Album album = albumRepository.findById(albumUid).orElseThrow(
                    () -> new EntityNotFoundException("Album is not Existed.")
            );

            List<PhotoInAlbum> photoInAlbums = photoInAlbumRepository.findAllByAlbum(album);

            photoInAlbumRepository.deleteAll(photoInAlbums);

            albumRepository.delete(album);

            return BooleanDto.builder()
                    .result(true)
                    .build();

        }catch(Exception e){
            e.printStackTrace();
            return BooleanDto.builder()
                    .result(false)
                    .msg(e.getMessage())
                    .build();
        }
    }

//
//    public String invite(Long albumUid, Long userUid){
//
//        String randomNum = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
//
//
//
//        return randomNum;
//    }
//
}
