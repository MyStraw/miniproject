package edu.pnu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.DTO.BoardDTO;
import edu.pnu.DTO.MapperUtil;
import edu.pnu.domain.Board;
import edu.pnu.domain.Like;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.LikeRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepo;

	@Autowired
	private LikeRepository likeRepo;

	@Autowired
	private MapperUtil mapperUtil;


	public List<BoardDTO> list() {
		List<Board> boards = boardRepo.findAll();
		return boards.stream().map(board -> mapperUtil.convertToDTO(board, BoardDTO.class))
				.collect(Collectors.toList());
	}

    public List<BoardDTO> listStationCode(int stationcode) {
        List<Board> boards = boardRepo.findBystationcode(stationcode);
        return boards.stream().map(board -> mapperUtil.convertToDTO(board, BoardDTO.class)).collect(Collectors.toList());
    }

    public BoardDTO create(int stationcode, String authorizationHeader, BoardDTO boardDto, MultipartFile file) {
        Board board = mapperUtil.convertToEntity(boardDto, Board.class);
        String jwtToken = authorizationHeader.replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwtkey")).build().verify(jwtToken).getClaim("username")
				.asString();
		board.setAuthor(username);
		board.setStationcode(stationcode);

		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String newFilename = UUID.randomUUID() + "_" + originalFilename; // 고유한 파일명 생성
			File destination = Paths.get("c:/temp/uploads/", newFilename).toFile();

			try {
				file.transferTo(destination); // 파일 저장
				board.setImage(newFilename); // DB에 파일명만 저장, 로컬에 파일을 저장. 이미지 컨트롤러에 로컬에 저장된거 display 하는게 있다.
				// board.setImagefile(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				// 에러 처리 로직 (예: 예외 던지기)
			}
		}        
        Board savedBoard = boardRepo.save(board);
        return mapperUtil.convertToDTO(savedBoard, BoardDTO.class);
    }
    
    public BoardDTO update(Integer id, BoardDTO boardDto) {
    	Board updateboard = boardRepo.findById(id).orElseThrow();
    	updateboard.setTitle(boardDto.getTitle());
        updateboard.setContent(boardDto.getContent());
        updateboard.setAuthor(boardDto.getAuthor()); // author 필드 업데이트 추가
        updateboard.setStationcode(boardDto.getStationcode());
        Board updatedBoard = boardRepo.save(updateboard);
        return mapperUtil.convertToDTO(updatedBoard, BoardDTO.class);
    }

	public void delete(Integer id) {
		boardRepo.deleteById(id);
	}

	
    public BoardDTO find(Integer id) {
    	return mapperUtil.convertToDTO(boardRepo.findById(id).orElse(null), BoardDTO.class);
    }
    

	public boolean hasUserLikedTheBoard(Member member, Board board) {
		Like like = likeRepo.findByMemberAndBoard(member, board);
		return like != null;
	}

}
