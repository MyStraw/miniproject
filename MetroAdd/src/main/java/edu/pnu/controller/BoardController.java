package edu.pnu.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.pnu.controller.interfaces.BoardControllerInterface;
import edu.pnu.domain.Board;
import edu.pnu.service.BoardService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor // 이걸 이용해서 생성자 주입
@Validated
@RequestMapping("/board")
public class BoardController implements BoardControllerInterface {

	// @Autowired 필드주입을 하지말고 생성자 주입을 해.
	private final BoardService boardService;

	
	// @PostMapping("/create/{stationcode}")
	// 이미지랑 같이 보내려면 multipart/form-data를 써야한다. 프론트에서도.
	// 리퀘스트바디랑 리퀘스트파람 같이 쓰는건 안좋다.
	// @RequestBody는 요청의 본문 전체를 읽어서 해당 객체로 변환하는데, 이미 @RequestParam으로 멀티파트 요청을 읽었다면
	// 본문은 이미 읽혀진 상태로 인식될 수 있습니다.
	// 지금 이미지랑 글이랑 둘다 동시에 보낼때 쓰는방법이다. 이거랑 properties에서
	// spring.servlet.multipart.enabled = true 이것도 해줘야한다.		
	
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
	
	@ResponseBody
	@Override
	@PostMapping(value = "/create/{stationcode}", consumes = "multipart/form-data")
	public ResponseEntity<Board> create(@PathVariable int stationcode,
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam("board") String boardStr) {
		try {
			Board board = boardService.create(stationcode, authorizationHeader, boardStr, image);
			return ResponseEntity.ok(board);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@ResponseBody
	@Override
	@GetMapping("/list")
	public List<Board> list() {
		return boardService.list();
	}

	@ResponseBody
	@Override
	@GetMapping("/list/{stationcode}")
	public List<Board> listStationCode(@PathVariable @Min(1) @Max(500) int stationcode) {
		return boardService.listStationCode(stationcode);
	}
	
//	@ResponseBody
//	@Override
//	@GetMapping("/list/{stationcode}")
//	public List<Board> listStationCode(@PathVariable @Saisai(min = 1, max = 500) int stationcode) {
//		return boardService.listStationCode(stationcode);
//	}	
	

	@ResponseBody
	@Override
	@GetMapping("/find/{id}")
	public Optional<Board> find(@PathVariable Integer id) {
		return boardService.find(id);
	}

	// Board board. Board 타입의 객체 board : 이게 받아온 json 객체. 리퀘스트바디 : 클라이언트의 요청. 즉
	// 클라이언트가 보낸게 board
	@ResponseBody
	@Override
	@PutMapping("/update/{id}")
	public Board update(@PathVariable Integer id, @Validated @RequestBody Board board) {
		return boardService.update(id, board);
	}

	@ResponseBody
	@Override
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		boardService.delete(id);
		return "게시글 삭제 성공";
	}
	
	
	@GetMapping("/view/{id}")
	public String viewBoard(@PathVariable Integer id, Model model) {
	    Optional<Board> board = boardService.find(id);
	    model.addAttribute("board", board.orElse(null));
	    return "boardView";
	}
	// BoardController.java
	// ...

	// 새로운 컨트롤러 메소드
	@GetMapping("/view")
	public String viewBoardById(@RequestParam Integer id, Model model) {
	  Optional<Board> board = boardService.find(id);
	  model.addAttribute("board", board.orElse(null));
	  return "boardView";
	}


}
