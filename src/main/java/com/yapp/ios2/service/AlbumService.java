package com.yapp.ios2.service;

import com.yapp.ios2.config.exception.AlbumNotFoundException;
import com.yapp.ios2.config.exception.EntityNotFoundException;
import com.yapp.ios2.config.exception.UserNotFoundException;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.BooleanDto;
import com.yapp.ios2.dto.PhotoInAlbumDto;
import com.yapp.ios2.dto.ResponseDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService{


    private final AlbumRepository albumRepository;

    private final AlbumOwnerRepository albumOwnerRepository;

    private final PhotoRepository photoRepository;

    private final PhotoInAlbumRepository photoInAlbumRepository;

    private final AlbumCoverRepository albumCoverRepository;

    private final AlbumLayoutRepository albumLayoutRepository;

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

        List<PhotoInAlbumDto> photoInfos = new ArrayList<>();

        photoInAlbumRepository.findAllByAlbum(newAlbum).forEach(
                photoInAlbum -> {
                    photoInfos.add(new PhotoInAlbumDto(photoInAlbum));
                }
        );

        albumDto = new AlbumDto(newAlbum, photoInfos);

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

    public Album getAlbum(Long albumUid){
        Album album = albumRepository.findById(albumUid).orElseThrow(
                () -> new EntityNotFoundException("Invalid AlbumUid")
        );
        return album;
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

                    List<PhotoInAlbumDto> photoInfos = new ArrayList<>();

                    photoInAlbumRepository.findAllByAlbum(album).forEach(
                            photoInAlbum -> {
                                photoInfos.add(new PhotoInAlbumDto(photoInAlbum));
                            }
                    );

                    albums.add(new AlbumDto(album, photoInfos));
                }
        );

        return albums;
    }

    public BooleanDto addPhotoInAlbum(Long albumUid, Long photoUid, Integer paperNum, Integer sequence) {

        String msg = "";

        try{
            Photo photo = photoRepository.findById(photoUid).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Photo UID (%d) is not Exist.", photoUid.intValue()))
            );
            Album album = albumRepository.findById(albumUid).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Album UID (%d) is not Exist.", albumUid.intValue()))
            );

            // 유호성 검사
            if( !photoInAlbumRepository.findAllByPaperNumAndSequence(paperNum, sequence).isEmpty() ){
                // 저장하려는 사진 위치에 이미 사진이 있는지 여부.
                msg = "Photo is Changed.";
            }else if ( album.getAlbumLayout().getTotPaper() < paperNum || album.getAlbumLayout().getPhotoPerPaper() < sequence ){
                // 저장하려는 위치가 정당한지 여부(전체 사진 페이지를 넘기지 않는지, 각 페이지별 들어가는 사진 순서에 해당하는 지)
                return BooleanDto.fail("Invalid PaperNum & Sequence.");
            }

            PhotoInAlbum photoInAlbum = PhotoInAlbum.builder()
                    .photo(photo)
                    .album(album)
                    .paperNum(paperNum)
                    .sequence(sequence)
                    .build();

            photoInAlbumRepository.save(photoInAlbum);

            if(msg.isBlank()) msg = "New Photo is added.";

            return BooleanDto.success(msg);

        }catch (Exception e){

            return BooleanDto.fail(e.getMessage());

        }

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

        return BooleanDto.success();
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
