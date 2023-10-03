package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;

public interface BoardRepository extends JpaRepository<Board, Integer> {

	List<Board> findBystationcodeOrderByLikesCountDesc(int stationcode);	
	List<Board> findByAuthor(String username);

    List<Board> findAllByLikes_Member(Member currentMember);
    List<Board> findAllByLikes_MemberOrderByLikes_BoardId(Member currentMember);
}
