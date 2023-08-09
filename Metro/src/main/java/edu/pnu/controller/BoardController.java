package edu.pnu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.pnu.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService; 
    
  //  @PostMapping("/create/{stationcode}")
    // 이미지랑 같이 보내려면 multipart/form-data를 써야한다. 프론트에서도.
    // 리퀘스트바디랑 리퀘스트파람 같이 쓰는건 안좋다.
    // @RequestBody는 요청의 본문 전체를 읽어서 해당 객체로 변환하는데, 이미 @RequestParam으로 멀티파트 요청을 읽었다면 본문은 이미 읽혀진 상태로 인식될 수 있습니다.
    // 지금 이미지랑 글이랑 둘다 동시에 보낼때 쓰는방법이다. 이거랑 properties에서 spring.servlet.multipart.enabled = true 이것도 해줘야한다.
  @PostMapping(value = "/create/{stationcode}", consumes = "multipart/form-data")
    public Board create(@PathVariable int stationcode, @RequestHeader("Authorization") String authorizationHeader, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam("board") String boardStr) throws JsonMappingException, JsonProcessingException { //Board board가 이미 받아온 json이다. Board 타입의 board 객체. 
    	//Requestbody가 client에서 보내온 json 정보이다. 
	  
	  	ObjectMapper mapper = new ObjectMapper();
	    Board board = mapper.readValue(boardStr, Board.class);
        return boardService.create(stationcode, authorizationHeader, board, image);
    }

    @GetMapping("/list")
    public List<Board> list() {
        return boardService.list();
    }
    
    @GetMapping("/list/{stationcode}")
    public List<Board> listStationCode(@PathVariable int stationcode){
    	return boardService.listStationCode(stationcode);
    }    
    
    @GetMapping("/find/{id}")
    public Optional<Board> find(@PathVariable Integer id){
    	return boardService.find(id);
    }   
    

    @PutMapping("/update/{id}")
    public Board update(@PathVariable Integer id, @RequestBody Board board) {
        return boardService.update(id, board);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        boardService.delete(id);
        return "게시글 삭제 성공";
    }
}


