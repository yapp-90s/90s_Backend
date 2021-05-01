package com.yapp.ios2.repository;


import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, Long>{

    List<Album> findAllByName(String Name);

    @Query("select a from Album a, AlbumOwner ao where ao.user = :user and a = ao.album")
    List<Album> findByUser(@Param("user")User user);

}
