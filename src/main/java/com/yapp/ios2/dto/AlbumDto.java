package com.yapp.ios2.dto;

import com.yapp.ios2.service.PhotoService;
import com.yapp.ios2.vo.Album;

import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlbumDto {
	
	private Long albumUid;
	
	private String name;
	
	private Integer readCnt;
	
	private Integer coverCode;
	
	private Integer layoutCode;

	private List<PhotoInAlbumDto> photos;
	
	private LocalDateTime completedAt;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private Boolean isComplete;

	public AlbumDto(Album album, List<PhotoInAlbumDto> photos){

		this.albumUid = album.getUid();
		this.name = album.getName();
		this.readCnt = album.getReadCnt();
		this.coverCode = album.getAlbumCover().getCode();
		this.layoutCode = album.getAlbumLayout().getCode();
		this.completedAt = album.getCompletedAt();
		this.createdAt = album.getCreatedAt();
		this.updatedAt = album.getUpdatedAt();
		this.isComplete = !ObjectUtils.isEmpty(album.getCompletedAt());

		this.photos = photos;
	}

}
