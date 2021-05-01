package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Override
    List<Photo> findAll();

    List<Photo> findAllByFilm(Film film);
}
