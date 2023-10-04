package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.jdbc.JdbcBoardRepository;
import edu.pnu.persistence.MemberRepository;
@Service
public class MyboardService {
	//@Autowired
	private MemberRepository memberRepo;
	
	//@Autowired
	private JdbcBoardRepository boardRepo;
	
	
	
	public List<Board> findBoardsByAuthor(String username) {
	    return boardRepo.findByAuthor(username);
	}

	public List<Board> findLikedBoardsByUsername(String username) {
	    Member currentMember = memberRepo.findByUsername(username).orElse(null);
	    if (currentMember == null) {
	        // 예외 처리
	    }
	    return boardRepo.findAllByLikes_MemberOrderByLikes_BoardId(currentMember);
	}	

}
