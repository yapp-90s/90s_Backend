package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Album;

import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	private Long coverUid;
	
	private Long layoutUid;
	
	private LocalDateTime completedAt;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private Boolean isComplete;

	public AlbumDto(Album album){
		this.albumUid = album.getUid();
		this.name = album.getName();
		this.readCnt = album.getReadCnt();
		this.coverUid = album.getAlbumCover().getUid();
		this.layoutUid = album.getAlbumLayout().getUid();
		this.completedAt = album.getCompletedAt();
		this.createdAt = album.getCreatedAt();
		this.updatedAt = album.getUpdatedAt();
		this.isComplete = ObjectUtils.isEmpty(album.getCreatedAt());
	}

}
