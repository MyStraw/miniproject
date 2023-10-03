package edu.pnu.controller.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import edu.pnu.domain.Board;

public interface BoardControllerInterface {
	ResponseEntity<Board> create(int stationcode, String authorizationHeader, MultipartFile image, String boardStr);

	List<Board> list();

	List<Board> listStationCode(int stationcode);

	Optional<Board> find(Integer id);

	Board update(Integer id, Board board);

	String delete(Integer id);
}
