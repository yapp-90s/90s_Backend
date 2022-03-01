package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Photo;
import lombok.*;
import org.springframework.util.ObjectUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PhotoDto implements IDto{

	private Long photoUid;
	
	private Long filmUid;

	private Long albumUid;

	public PhotoDto(Photo photo){
		this.photoUid = photo.getUid();
		this.filmUid = photo.getFilm().getUid();
		this.albumUid = ObjectUtils.isEmpty(photo.getAlbum()) ? null : photo.getAlbum().getUid();
	}

}
