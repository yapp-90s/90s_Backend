package com.yapp.ios2.repository;

import com.yapp.ios2.vo.AlbumCover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumCoverRepository extends JpaRepository<AlbumCover, Long> {

    AlbumCover findAlbumCoverByCode(Integer code);

}
