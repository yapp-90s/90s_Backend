package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Album;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlbumDto {
	
	private Integer albumUid;
	
	private String name;
	
	private String password;
	
	private Integer photoLimit;
	
	private Integer count;
	
	private Integer coverUid;
	
	private Integer layoutUid;
	
	private LocalDateTime endAt;
	
	private LocalDateTime createAt;
	
	private LocalDateTime updateAt;
	
	private Boolean isComplete;

}
