package edu.pnu.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;

public interface BoardServiceInterface {
	List<Board> list();

	List<Board> listStationCode(int stationcode);

	Board create(int stationcode, String authorizationHeader, String boardStr, MultipartFile file)
			throws JsonProcessingException, IOException;

	Board update(Integer id, Board board);

	void delete(Integer id);

	Optional<Board> find(Integer id);

	boolean hasUserLikedTheBoard(Member member, Board board);

	public String parseJWTToken(String authorizationHeader);

	public void handleFileUpload(MultipartFile file, Board board);
}
