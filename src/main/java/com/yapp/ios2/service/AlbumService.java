//package com.yapp.ios2.service;
//
//import com.yapp.ios2.dto.AlbumDto;
//import com.yapp.ios2.dto.AlbumOwnerDto;
//import com.yapp.ios2.repository.*;
//import com.yapp.ios2.config.exception.AlbumNotFoundException;
//import com.yapp.ios2.repository.AlbumOwnerRepository;
//import com.yapp.ios2.repository.AlbumRepository;
//import com.yapp.ios2.repository.UserRepository;
//import com.yapp.ios2.vo.*;
//import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.chrono.ChronoLocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Service
//public class AlbumService{
//
//    @Autowired
//    AlbumRepository albumRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    AlbumOwnerRepository albumOwnerRepository;
//
//    @Autowired
//    PhotoRepository photoRepository;
//
//    @Autowired
//    S3Service s3Service;
//
//    @Autowired
//    CoverRepository coverRepository;
//
//    public Album create(String name, Integer photoLimit, Long user, Long cover, LocalDate endDate) {
//
//        Album newAlbum = Album.builder()
//                .name(name)
//                .password(UUID.randomUUID().toString())
//                .photoLimit(photoLimit)
//                .cover(coverRepository.findById(cover).get())
//                .endDate(endDate)
//                .count(0)
//                .build();
//
//        albumRepository.save(newAlbum);
//
//        addOwner(newAlbum.getUid(), user, "ROLE_CREATOR");
//
//        return newAlbum;
//    }
//
//    public AlbumOwner addOwner(Long albumUid, Long user, String role){
//
//        AlbumOwner albumOwner = AlbumOwner.builder()
//                .album(albumRepository.findById(albumUid).get())
//                .user(userRepository.findById(user).get())
//                .role(role)
//                .build();
//
//        albumOwnerRepository.save(albumOwner);
//
//        return albumOwner;
//    }
//
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
//    public List<Album> getAlbumsByUser(User user) {
//
//        List<Album> albums = albumRepository.findByUser(user);
//
//        return albums;
//    }
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
//    public void plusCount(Long albumUid){
//        Album album = albumRepository.findById(albumUid).
//                orElseThrow(() -> new AlbumNotFoundException());
//
//        Integer count = album.getCount();
//        album.setCount(++count);
//        albumRepository.save(album);
//
//    }
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
//    public void albumStatusChecker(Long albumUid){
//
//    }
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
//}
