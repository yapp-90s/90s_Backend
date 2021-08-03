package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.PhotoInAlbum;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoInAlbumRepository extends JpaRepository<PhotoInAlbum, Long> {

    @Query("select p from PhotoInAlbum p where p.paperNum = :paperNum and p.sequence = :sequence")
    List<PhotoInAlbum> findAllByPaperNumAndSequence(@Param("paperNum")Integer paperNum, @Param("sequence")Integer sequence);

    List<PhotoInAlbum> findAllByAlbum(Album album);
}
