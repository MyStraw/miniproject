package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Board;
import edu.pnu.domain.Like;
import edu.pnu.domain.Member;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    Like findByMemberAndBoard(Member member, Board board);

}
