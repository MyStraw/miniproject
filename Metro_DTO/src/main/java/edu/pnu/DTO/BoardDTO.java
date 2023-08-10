package edu.pnu.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

	private Integer id;
	private String title;
	private String content;
	private String author;
	private Integer stationcode;
	private List<Integer> likesIds; // Like 객체의 ID만 가지고 있음
	private Integer likesCount;
	private String image;

}
