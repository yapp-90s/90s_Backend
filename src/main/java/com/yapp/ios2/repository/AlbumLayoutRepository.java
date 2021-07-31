package com.yapp.ios2.repository;

import com.yapp.ios2.vo.AlbumLayout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumLayoutRepository extends JpaRepository<AlbumLayout, Long> {

    Optional<AlbumLayout> findAlbumLayoutByCode(Integer code);
}
