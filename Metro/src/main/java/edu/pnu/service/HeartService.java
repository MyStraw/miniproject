package edu.pnu.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.domain.Heart;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.HeartRepository;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartService {
	private final HeartRepository heartRepo;
	private final MemberRepository memberRepo;
	private final BoardRepository boardRepo;

	@Transactional
	public void addLike(String loginId, Integer boardId) {
		
		 Optional<Board> optionalBoard = boardRepo.findById(boardId);
		    if (!optionalBoard.isPresent()) {
		        throw new IllegalArgumentException("Board not found with ID: " + boardId);
		    }
		    Board board = optionalBoard.get();

		    Optional<Member> optionalMember = memberRepo.findByUsername(loginId);
		    if (!optionalMember.isPresent()) {
		        throw new IllegalArgumentException("Member not found with username: " + loginId);
		    }
		    Member loginUser = optionalMember.get();

		    board.likeChange(board.getLikecount() + 1);
		
		
		
//		Board board = boardRepo.findById(boardId).get();
//		Member loginUser = memberRepo.findByUsername(loginId).get();
////		Member boardUser = board.getAuthor();
////
////		// 자신이 누른 좋아요가 아니라면
////		if (!boardUser.equals(loginUser)) {
////			boardUser.likeChange(boardUser.getReceivedLikeCnt() + 1);
////		}
//		board.likeChange(board.getLikecount() + 1);

		heartRepo.save(Heart.builder().member(loginUser).board(board).build());
	}

	@Transactional
	public void deleteLike(String loginId, Integer boardId) {
		
	    Optional<Board> optionalBoard = boardRepo.findById(boardId);
	    if (!optionalBoard.isPresent()) {
	        throw new IllegalArgumentException("Board not found with ID: " + boardId);
	    }
	    Board board = optionalBoard.get();

	    Optional<Member> optionalMember = memberRepo.findByUsername(loginId);
	    if (!optionalMember.isPresent()) {
	        throw new IllegalArgumentException("Member not found with username: " + loginId);
	    }
	    Member loginUser = optionalMember.get();

	    board.likeChange(board.getLikecount() - 1);
	    heartRepo.deleteByMemberUsernameAndBoardId(loginId, boardId);		
		
//		Board board = boardRepo.findById(boardId).get();
//		Member loginUser = memberRepo.findByUsername(loginId).get();
////        Member boardUser = board.getAuthor();
//
//		// 자신이 누른 좋아요가 아니라면
////        if (!boardUser.equals(loginUser)) {
////            boardUser.likeChange(boardUser.getReceivedLikeCnt() - 1);
////        }
//		board.likeChange(board.getLikecount() - 1);
//
//		heartRepo.deleteByMemberUsernameAndBoardId(loginId, boardId);
	}

	public Boolean checkLike(String loginId, Integer boardId) {
		return heartRepo.existsByMemberUsernameAndBoardId(loginId, boardId);
	}

}
