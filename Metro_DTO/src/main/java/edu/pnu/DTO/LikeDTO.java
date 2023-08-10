package edu.pnu.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

	private Long id;
	private String memberName; // Member의 username
	private Long boardId; // Board의 id

	// 생성자, getter, setter ...

}
