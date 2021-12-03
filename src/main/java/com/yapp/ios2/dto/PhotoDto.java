package com.yapp.ios2.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PhotoDto {

	private Integer photoUid;
	
	private String url;
	
	private String filmUid;
	
}
