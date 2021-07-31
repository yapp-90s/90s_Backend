package com.yapp.ios2.repository;

import com.yapp.ios2.vo.AlbumCover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumCoverRepository extends JpaRepository<AlbumCover, Long> {

    Optional<AlbumCover> findAlbumCoverByCode(Integer code);

}
