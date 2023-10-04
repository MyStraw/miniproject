package edu.pnu.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.jdbc.JdbcBoardRepository;
import edu.pnu.service.MyboardService;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor //이걸 이용해서 생성자 주입
@RequestMapping("/board")
public class MyboardController {

	
	//@Autowired
	private final JdbcBoardRepository boardRepo;

	//@Autowired 필드주입을 하지말고 생성자 주입을 해. 
	private final MyboardService myboardService;
	
	@GetMapping("/myboards")
	public ResponseEntity<List<Board>> getMyBoards(Principal principal) {
	    String username = principal.getName();
	    List<Board> myBoards = boardRepo.findByAuthor(username);
	    return ResponseEntity.ok(myBoards);
	}

	@GetMapping("/mylikedboards")
	public ResponseEntity<List<Board>> getMyLikedBoards(Principal principal) {
	    String username = principal.getName();
	    List<Board> likedBoards = myboardService.findLikedBoardsByUsername(username);
	    return ResponseEntity.ok(likedBoards);
	}

}
