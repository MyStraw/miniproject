package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/register")
	public String register(@RequestBody Member member) {
		if (memberService.registerMember(member) != null) {
			return "회원가입 성공";
		} else {
			return "회원가입 실패";
		}
	}

//	@PostMapping("/register")
//	public ResponseEntity<Map<String, String>> register(@RequestBody Member member) {
//	    memberService.registerMember(member);
//	    Map<String, String> response = new HashMap<>();
//	    response.put("message", "회원가입 성공");
//	    return ResponseEntity.ok(response);
//	}

}
