package edu.pnu.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.LikeService;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor //이걸 이용해서 생성자 주입
@RequestMapping("/board")
public class LikeController {
	
	private final LikeService likeService;
	
	
	@GetMapping("{id}/like") 
	public ResponseEntity<Void> toggleLike(@PathVariable Integer id, Principal principal) {
		likeService.toggleLike(principal.getName(), id);
		return ResponseEntity.ok().build(); 
	} 

	
	 @GetMapping("{id}/checkliked")
	    public ResponseEntity<Boolean> hasUserLikedTheBoard(@PathVariable Integer id, Principal principal) {
	        boolean hasLiked = likeService.hasUserLikedTheBoard(principal.getName(), id);
	        return ResponseEntity.ok(hasLiked);
	    }

}
