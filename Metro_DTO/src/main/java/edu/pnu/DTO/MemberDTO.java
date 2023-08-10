package edu.pnu.DTO;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private String username;
	private String password; // 보안 문제로, 실제 API 응답에 포함되면 안 됩니다.
	private String role;
	private boolean enabled;
	private Date date;
	private List<Integer> likesIds; // Like 객체의 ID만 가지고 있음

}
