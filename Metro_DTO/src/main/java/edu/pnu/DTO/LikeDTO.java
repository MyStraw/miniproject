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

	private Integer id;
	private String memberUsername; // Member의 username만 가지고 있음
	private Integer boardId; // Board의 ID만 가지고 있음

}
