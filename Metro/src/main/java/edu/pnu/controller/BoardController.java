package edu.pnu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.BoardService;
import edu.pnu.service.LikeService;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private LikeService likeService;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;

	// @PostMapping("/create/{stationcode}")
	// 이미지랑 같이 보내려면 multipart/form-data를 써야한다. 프론트에서도.
	// 리퀘스트바디랑 리퀘스트파람 같이 쓰는건 안좋다.
	// @RequestBody는 요청의 본문 전체를 읽어서 해당 객체로 변환하는데, 이미 @RequestParam으로 멀티파트 요청을 읽었다면
	// 본문은 이미 읽혀진 상태로 인식될 수 있습니다.
	// 지금 이미지랑 글이랑 둘다 동시에 보낼때 쓰는방법이다. 이거랑 properties에서
	// spring.servlet.multipart.enabled = true 이것도 해줘야한다.

	
	
	
//	@PostMapping(value = "/create/{stationcode}", consumes = "multipart/form-data")
//	public ResponseEntity<?> create(@PathVariable int stationcode, @RequestHeader("Authorization") String authorizationHeader,
//			@RequestParam(value = "image", required = false) MultipartFile image,
//			@RequestParam("board") String boardStr) throws JsonMappingException, JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		Board board = mapper.readValue(boardStr, Board.class);
//		Board createdBoard = boardService.create(stationcode, authorizationHeader, board, image);
//		
//		if(createdBoard != null) {
//			return ResponseEntity.ok(createdBoard);
//		} else {
//			return ResponseEntity.badRequest().body("보드 생성에 실패했습니다.");
//		}
//	}
	
	//ObjectMapper 이용
	//ObjectMapper mapper = new ObjectMapper();
	//1.Java 객체를 Json으로변환하려면 
//	Board board = new Board();
//	String jsonString = mapper.writeValueAsString(board);
	
	//2.JSON을 Java 객체로 변환하려면
//	String jsonString = "{...}"; // 여기에는 실제 JSON 문자열이 들어가야 합니다.
//	Board board = mapper.readValue(jsonString, Board.class);


	
	//리액트에서 나에게 보내줄때 "board"라는 키로 안에 title이랑 content를 넣어서 보내줬다.
	//"board"는 json 형태로 body(본문)에 넣어보내는 정보의 키다. body의 타입을 multipart/form-data 이걸로 지정해줬다.
	//post나 put은 HTTP 요청을 body에 정보를 담아 보내는것이다.
	@PostMapping(value = "/create/{stationcode}", consumes = "multipart/form-data")
	public Board create(@PathVariable int stationcode, @RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam("board") String boardStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper(); //java 객체를 json으로 혹은 json을 java 객체로 변환하는 클래스.
		Board board = mapper.readValue(boardStr, Board.class);
		//문자열 json 형태의 문자열 boardStr을 Board 클래스의 인스턴스로 변환
		return boardService.create(stationcode, authorizationHeader, board, image);
	}
	
	@GetMapping("/list")
	public List<Board> list() {
		return boardService.list();
	}

	@GetMapping("/list/{stationcode}")
	public List<Board> listStationCode(@PathVariable int stationcode) {
		return boardService.listStationCode(stationcode);
	}

	@GetMapping("/find/{id}")
	public Optional<Board> find(@PathVariable Integer id) {
		return boardService.find(id);
	}

	// Board board. Board 타입의 객체 board : 이게 받아온 json 객체. 리퀘스트바디 : 클라이언트의 요청. 즉
	// 클라이언트가 보낸게 board
	
	@PutMapping("/update/{id}")
	public Board update(@PathVariable Integer id, @RequestBody Board board) {
		return boardService.update(id, board);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		boardService.delete(id);
		return "게시글 삭제 성공";
	}

	// ------------------좋아요 부분--------------------//

	@GetMapping("{id}/like") //Principal 보안관련 인터페이스. 현재 사용자의 인증정보 및 세부정보 나타냄.
	//인증거친후 Authentication 객체가 생성되고 이게 Principal을 구현하고 있다. 여기서 Principal 파라미터를 사용하는건
	//Authentication 객체를 자동으로 주입한다는것.
	public ResponseEntity<Void> toggleLike(@PathVariable Integer id, Principal principal) {
		likeService.toggleLike(principal.getName(), id);
		return ResponseEntity.ok().build(); //HTTP 응답을 생성하고 반환. ResponseEntity:HTTP 응답 나타내는 클래스.
	} // ok는 상태코드 200(ok)를 나타내는 ResponseEntity를 반환하는 메소드. 404는 not found.
	//build()는 생성된 정보를 바탕으로 ResponseEntit객체를 생성하는 메소드.

	
	//Optional : java8부터 나온 컨테이너 객체. 값이 있을수도 있고 없을수도 있는 상황을 대응하기 위해 사용됨.
	//반환값이 null이 될수도 있는 상황에서 null 참조를 피하고자 할때 유용하게 사용된다.
	//기존엔 null일땐 예외처리를 직접 해줬지만, 이걸 사용하면 null검사와 예외처리를 더 체계적으로 가능.
	@GetMapping("{id}/checkliked")
	public ResponseEntity<Boolean> hasUserLikedTheBoard(@PathVariable Integer id, Principal principal) {
	    // 현재 로그인한 사용자 정보를 가져옵니다.
	    Optional<Member> optionalMember = memberRepo.findByUsername(principal.getName());
	    //해당 사용자 이름을 가진 Member 객체가 존재할수도, 존재하지 않을수도.
	    if (!optionalMember.isPresent()) { //컨테이너값 존재하면 true, 아니면 false.
	        // 에러 처리 로직 (예: 예외 던지기)
	    }
	    Member currentMember = optionalMember.get();
	    // 조회할 게시물을 가져옵니다.
	    Board targetBoard = boardService.find(id).orElse(null);
	    
	    if (targetBoard == null) {
	        return ResponseEntity.notFound().build(); // 게시물이 없을 경우 404 응답
	    }
	    
	    boolean hasLiked = boardService.hasUserLikedTheBoard(currentMember, targetBoard);
	    return ResponseEntity.ok(hasLiked); // 사용자가 게시물에 좋아요를 눌렀는지 안 눌렀는지를 반환합니다.
	}
	
	@GetMapping("/myboards")
	public ResponseEntity<List<Board>> getMyBoards(Principal principal) {
	    String username = principal.getName();
	    List<Board> myBoards = boardRepo.findByAuthor(username);
	    return ResponseEntity.ok(myBoards);
	}

	@GetMapping("/mylikedboards")
	public ResponseEntity<List<Board>> getMyLikedBoards(Principal principal) {
	    String username = principal.getName();
	    Member currentMember = memberRepo.findByUsername(username).orElse(null);
	    if (currentMember == null) {
	        return ResponseEntity.notFound().build();
	    }
	    List<Board> likedBoards = boardRepo.findAllByLikes_MemberOrderByLikes_BoardId(currentMember);
	    return ResponseEntity.ok(likedBoards);
	}

}
