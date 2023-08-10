package edu.pnu.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

	private Long id;
	private String title;
	private String content;
	private String authorName; // Member 객체 대신 단순한 이름 문자열로 표현
	private int likesCount; // 좋아요 수를 단순한 정수로 표현

	// 생성자, getter, setter ...

}
