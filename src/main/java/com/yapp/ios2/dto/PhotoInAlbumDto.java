package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.PhotoInAlbum;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PhotoInAlbumDto {

    private Long photoUid;

    private String url;

    private Integer paperNum;

    private Integer sequence;

    public PhotoInAlbumDto(PhotoInAlbum photoInAlbum){
        this.photoUid = photoInAlbum.getPhoto().getUid();
        this.paperNum = photoInAlbum.getPaperNum();
        this.sequence = photoInAlbum.getSequence();
    }

}
