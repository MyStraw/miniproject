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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Board;
import edu.pnu.domain.Like;
import edu.pnu.domain.Member;
import edu.pnu.jdbc.JdbcBoardRepository;
import edu.pnu.persistence.LikeRepository;
import edu.pnu.service.interfaces.BoardServiceInterface;
import lombok.extern.slf4j.Slf4j;


@Slf4j //로그 써볼거니까
@Service // 서비스 빈 관리
public class BoardService implements BoardServiceInterface {


	@Autowired // 자동으로 빈 주입하게~
	private JdbcBoardRepository boardRepo;

	@Autowired
	private LikeRepository likeRepo;

	@Override
	public List<Board> list() {
		return boardRepo.findAll(); //모든 게시판 리스트 반환(모두 반환할 필요가 어디에 있었는지 확인)
	}

	@Override
	public List<Board> listStationCode(int stationcode) {
		return boardRepo.findBystationcodeOrderByLikesCountDesc(stationcode);
	}

	@Override
	public Board create(int stationcode, String authorizationHeader, String boardStr, MultipartFile file)
			throws JsonMappingException, JsonProcessingException {
		// JSON 문자열을 Board 객체로 변환
		ObjectMapper mapper = new ObjectMapper();
		Board board = mapper.readValue(boardStr, Board.class);

		// 유효성 검사 코드 이렇게 쓰는거 맞나? 
		validateBoard(board);

		// JWT 토큰 파싱, 유저 이름을 추출해냄
		String username = parseJWTToken(authorizationHeader);
		board.setAuthor(username);

		// Station Code 설정
		board.setStationcode(stationcode);

		// 파일 저장
		handleFileUpload(file, board);

		//게시판 저장 후 리턴
		return boardRepo.save(board);
	}

	//파싱 부분도 유효성 검사가 있으면 좋지 않을까?
	@Override
	public String parseJWTToken(String authorizationHeader) {
		//Bearer 글자를 붙여서 토큰을 줬으니 이거 제거하고 써야겠지
		String jwtToken = authorizationHeader.replace("Bearer ", "");
		return JWT.require(Algorithm.HMAC256("edu.pnu.jwtkey")).build().verify(jwtToken).getClaim("username")
				.asString();
	}

	@Override
	public void handleFileUpload(MultipartFile file, Board board) {
		//파일이 null 아닐때, 비어있지 않을때만 업로드
		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String newFilename = UUID.randomUUID() + "_" + originalFilename;
			File destination = Paths.get("c:/temp/uploads/", newFilename).toFile();

			try { //파일을 위의 경로로 이동
				file.transferTo(destination);
				board.setImage(newFilename);
			} catch (IOException e) {
				e.printStackTrace();
				// 일단 간단하게
			}
		}
	}

	@Override
	public Board update(Integer id, Board board) {

		Board updateboard = boardRepo.findById(id).orElseThrow();
		updateboard.setTitle(board.getTitle());
		updateboard.setContent(board.getContent());
		return boardRepo.save(updateboard);
	}

	@Override
	public void delete(Integer id) {
		boardRepo.deleteById(id);
	}

	@Override
	public Optional<Board> find(Integer id) {
		return boardRepo.findById(id);
	}

	@Override
	public boolean hasUserLikedTheBoard(Member member, Board board) {
		Like like = likeRepo.findByMemberAndBoard(member, board);
		return like != null;
	}
	
	
	
	public class BoardValidationException extends RuntimeException {
	    public BoardValidationException(String message) {
	        super(message);
	    }
	}
	
	 private void validateBoard(Board board) {	        
	        if (board.getTitle() == null || board.getTitle().isEmpty()) {
	            log.error("게시글의 제목이 없습니다.");
	            throw new BoardValidationException("게시글의 제목이 없습니다.");
	        }
	        if (board.getContent() == null || board.getContent().isEmpty()) {
	            log.error("게시글의 내용이 없습니다.");
	            throw new BoardValidationException("게시글의 내용이 없습니다.");
	        }
	        
	    }

}
