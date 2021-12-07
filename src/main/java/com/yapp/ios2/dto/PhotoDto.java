package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Photo;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PhotoDto {

	private Long photoUid;
	
	private String url;
	
	private Long filmUid;

	public PhotoDto(Photo photo){
		this.photoUid = photo.getUid();
		this.url = photo.getUrl();
		this.filmUid = photo.getFilm().getUid();
	}

}
