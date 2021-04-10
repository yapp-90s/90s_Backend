package com.yapp.ios2.repository;

import com.yapp.ios2.vo.AlbumLayout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumLayoutRepository extends JpaRepository<AlbumLayout, Long> {

    AlbumLayout findAlbumLayoutByCode(Integer code);
}
