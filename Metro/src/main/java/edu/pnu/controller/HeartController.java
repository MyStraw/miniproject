package edu.pnu.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.BoardService;
import edu.pnu.service.HeartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class HeartController {

	private final HeartService heartService;
	private final BoardService boardService;

	@GetMapping("/add/{boardId}")
	public void addLike(@PathVariable Integer boardId, Authentication auth) {
		heartService.addLike(auth.getName(), boardId);
		//return "redirect:/boards/" + boardId;
	}

	@GetMapping("/delete/{boardId}")
	public void deleteLike(@PathVariable Integer boardId, Authentication auth) {
		heartService.deleteLike(auth.getName(), boardId);
		//return "redirect:/boards/" + boardId;
	}

}
