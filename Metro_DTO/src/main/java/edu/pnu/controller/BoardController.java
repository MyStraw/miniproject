package edu.pnu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import edu.pnu.DTO.BoardDTO;
import edu.pnu.DTO.MapperUtil;
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

	@Autowired
	private MapperUtil mapperUtil;

	// @PostMapping("/create/{stationcode}")
	// 이미지랑 같이 보내려면 multipart/form-data를 써야한다. 프론트에서도.
	// 리퀘스트바디랑 리퀘스트파람 같이 쓰는건 안좋다.
	// @RequestBody는 요청의 본문 전체를 읽어서 해당 객체로 변환하는데, 이미 @RequestParam으로 멀티파트 요청을 읽었다면
	// 본문은 이미 읽혀진 상태로 인식될 수 있습니다.
	// 지금 이미지랑 글이랑 둘다 동시에 보낼때 쓰는방법이다. 이거랑 properties에서
	// spring.servlet.multipart.enabled = true 이것도 해줘야한다.

//	@PostMapping(value = "/create/{stationcode}", consumes = "multipart/form-data")
//	public Board create(@PathVariable int stationcode, @RequestHeader("Authorization") String authorizationHeader,
//			@RequestParam(value = "image", required = false) MultipartFile image,
//			@RequestParam("board") String boardStr) throws JsonMappingException, JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		Board board = mapper.readValue(boardStr, Board.class);
//		return boardService.create(stationcode, authorizationHeader, board, image);
//	}

	@PostMapping(value = "/create/{stationcode}", consumes = "multipart/form-data")
	public BoardDTO create(@PathVariable int stationcode, @RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam("board") String boardStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		BoardDTO boardDto = mapper.readValue(boardStr, BoardDTO.class);
		return boardService.create(stationcode, authorizationHeader, boardDto, image);
	}

//	@GetMapping("/list")
//	public List<Board> list() {
//		return boardService.list();
//	}

	@GetMapping("/list")
	public List<BoardDTO> list() {
		return boardService.list();
	}

//	@GetMapping("/list/{stationcode}")
//	public List<Board> listStationCode(@PathVariable int stationcode) {
//		return boardService.listStationCode(stationcode);
//	}

	@GetMapping("/list/{stationcode}")
	public List<BoardDTO> listStationCode(@PathVariable int stationcode) {
		return boardService.listStationCode(stationcode);
	}

//
//	@GetMapping("/find/{id}")
//	public Optional<Board> find(@PathVariable Integer id) {
//		return boardService.find(id);
//	}
//
	@GetMapping("/find/{id}")
	public BoardDTO find(@PathVariable Integer id) {
		return boardService.find(id);
	}

//	// Board board. Board 타입의 객체 board : 이게 받아온 json 객체. 리퀘스트바디 : 클라이언트의 요청. 즉
//	// 클라이언트가 보낸게 board
//	@PutMapping("/update/{id}")
//	public Board update(@PathVariable Integer id, @RequestBody Board board) {
//		return boardService.update(id, board);
//	}

	@PutMapping("/update/{id}")
	public BoardDTO update(@PathVariable Integer id, @RequestBody BoardDTO boardDto) {
		return boardService.update(id, boardDto);
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		boardService.delete(id);
		return "게시글 삭제 성공";
	}

	// ------------------좋아요 부분--------------------//

	@PostMapping("{id}/like")
	public ResponseEntity<Void> toggleLike(@PathVariable Integer id, Principal principal) {
		likeService.toggleLike(principal.getName(), id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("{id}/checkliked")
	public ResponseEntity<Boolean> hasUserLikedTheBoard(@PathVariable Integer id, Principal principal) {
		// 현재 로그인한 사용자 정보를 가져옵니다.
		Optional<Member> optionalMember = memberRepo.findByUsername(principal.getName());
		if (!optionalMember.isPresent()) {
			// 에러 처리 로직 (예: 예외 던지기)
		}
		Member currentMember = optionalMember.get();
		// 조회할 게시물을 가져옵니다.
		 BoardDTO targetBoardDTO = boardService.find(id);

		if (targetBoardDTO  == null) {
			return ResponseEntity.notFound().build(); // 게시물이 없을 경우 404 응답
		}
		
		Board targetBoard = mapperUtil.convertToEntity(targetBoardDTO, Board.class);  

		boolean hasLiked = boardService.hasUserLikedTheBoard(currentMember, targetBoard);
		return ResponseEntity.ok(hasLiked); // 사용자가 게시물에 좋아요를 눌렀는지 안 눌렀는지를 반환합니다.
	}

	@GetMapping("/myboards")
	public ResponseEntity<List<BoardDTO>> getMyBoards(Principal principal) {
	    String username = principal.getName();
	    List<Board> myBoards = boardRepo.findByAuthor(username);
	    List<BoardDTO> myBoardDTOs = myBoards.stream()
	            .map(board -> mapperUtil.convertToDTO(board, BoardDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(myBoardDTOs);
	}

	
	@GetMapping("/mylikedboards")
	public ResponseEntity<List<BoardDTO>> getMyLikedBoards(Principal principal) {
	    String username = principal.getName();
	    Member currentMember = memberRepo.findByUsername(username).orElse(null);
	    if (currentMember == null) {
	        return ResponseEntity.notFound().build();
	    }
	    List<Board> likedBoards = boardRepo.findAllByLikes_Member(currentMember);
	    List<BoardDTO> likedBoardDTOs = likedBoards.stream()
	            .map(board -> mapperUtil.convertToDTO(board, BoardDTO.class))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(likedBoardDTOs);
	}

}
