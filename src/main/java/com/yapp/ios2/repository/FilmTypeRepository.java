package com.yapp.ios2.repository;

import com.yapp.ios2.vo.FilmType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmTypeRepository extends JpaRepository<FilmType, Long> {
    Optional<FilmType> findByName(String Name);

    Optional<FilmType> findByCode(Integer filmCode);
}
