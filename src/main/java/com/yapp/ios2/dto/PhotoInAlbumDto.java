package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Photo;
import com.yapp.ios2.vo.PhotoInAlbum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@Data
public class PhotoInAlbumDto implements IDto {

    private Long photoUid;

    private Long albumUid;

    private Integer paperNum;

    private Integer sequence;

    public PhotoInAlbumDto(PhotoInAlbum photoInAlbum){
        this.photoUid = photoInAlbum.getPhoto().getUid();
        this.albumUid = photoInAlbum.getAlbum().getUid();
        this.paperNum = photoInAlbum.getPaperNum();
        this.sequence = photoInAlbum.getSequence();
    }

}
