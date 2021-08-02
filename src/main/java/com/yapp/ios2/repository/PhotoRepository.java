package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Override
    List<Photo> findAll();

    List<Photo> findAllByFilm(Film film);

    @Query("select p from Photo p, Film f where p.film = f and f.user = :user")
    List<Photo> findAllByUser(@Param("user") User user);
}
