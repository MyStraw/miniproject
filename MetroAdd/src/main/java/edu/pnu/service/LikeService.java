package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.Like;
import edu.pnu.domain.Member;
import edu.pnu.jdbc.JdbcBoardRepository;
import edu.pnu.persistence.LikeRepository;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepo;

	@Autowired
	private JdbcBoardRepository boardRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private BoardService boardService;

	@Transactional
	public void toggleLike(String username, Integer boardId) { // findByUsername 이랑 ById는 미리 레포지토리에 만들어놨네
		Member member = memberRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
		Board board = boardRepo.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

		Like existLike = likeRepo.findByMemberAndBoard(member, board);

		if (existLike != null) {
			board.removeLike();
			likeRepo.delete(existLike);

		} else {
			board.addLike();
			Like newLike = new Like(member, board);
			likeRepo.save(newLike);
		}
	}

	public boolean hasUserLikedTheBoard(String username, Integer boardId) {
		Optional<Member> optionalMember = memberRepo.findByUsername(username);
		if (!optionalMember.isPresent()) {
			// 예외 처리
		}
		Member currentMember = optionalMember.get();
		Board targetBoard = boardService.find(boardId).orElse(null);

		if (targetBoard == null) {
			// 예외 처리
		}

		return boardService.hasUserLikedTheBoard(currentMember, targetBoard);

	}
}
