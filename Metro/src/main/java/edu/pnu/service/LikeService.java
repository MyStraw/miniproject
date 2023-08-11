package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.Like;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.LikeRepository;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class LikeService {
	
	@Autowired
	private LikeRepository likeRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Transactional
	public void toggleLike(String username, Integer boardId) { //findByUsername 이랑 ById는 미리 레포지토리에 만들어놨네
		Member member = memberRepo.findByUsername(username).orElseThrow(()-> new RuntimeException("회원을 찾을 수 없습니다."));
		Board board = boardRepo.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
		
		Like existLike = likeRepo.findByMemberAndBoard(member,board);
		
		if (existLike !=null) {
			board.removeLike();
			likeRepo.delete(existLike);
		
		}else {
			board.addLike();
			Like newLike = new Like(member, board);
			likeRepo.save(newLike);
		}
	}
}
