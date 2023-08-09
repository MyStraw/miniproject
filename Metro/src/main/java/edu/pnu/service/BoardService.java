package edu.pnu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepo;
	
	 
	public List<Board> list() {		
		return boardRepo.findAll();
	}	
	
	public List<Board> listStationCode(int stationcode){
		return boardRepo.findBystationcode(stationcode);		
	}		
	
	public Board create(int stationcode, String authorizationHeader, Board board,  MultipartFile file) {	
		String jwtToken = authorizationHeader.replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwtkey")).build().verify(jwtToken).getClaim("username")
				.asString();		
		board.setAuthor(username);		
		board.setStationcode(stationcode);
		board.setLikecount(0);
		
		 if (file != null && !file.isEmpty()) {
		        String originalFilename = file.getOriginalFilename();
		        String newFilename = UUID.randomUUID() + "_" + originalFilename; // 고유한 파일명 생성
		        File destination = Paths.get("c:/temp/uploads/", newFilename).toFile();

		        try {
		            file.transferTo(destination); // 파일 저장
		            board.setImage(newFilename); // DB에 파일명만 저장, 로컬에 파일을 저장. 이미지 컨트롤러에 로컬에 저장된거 display 하는게 있다.
		            board.setImagefile(file.getBytes());
		        } catch (IOException e) {
		            e.printStackTrace();
		            // 에러 처리 로직 (예: 예외 던지기)
		        }
		    }		
		
		return boardRepo.save(board);
	}
	 
	public Board update(Integer id, Board board) {
		Board updateboard = boardRepo.findById(id).orElseThrow(); // 예외 처리 필요
		updateboard.setTitle(board.getTitle());		
		updateboard.setContent(board.getContent());       
        return boardRepo.save(updateboard);		
	}
	
	public void delete(Integer id) {
		boardRepo.deleteById(id);
	}

	public Optional<Board> find(Integer id) {		
		return boardRepo.findById(id);
	}
}
