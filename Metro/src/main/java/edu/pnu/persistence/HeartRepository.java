package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Heart;

public interface HeartRepository extends JpaRepository<Heart, Integer> {
	void deleteByMemberUsernameAndBoardId(String loginId, Integer boardId);
    Boolean existsByMemberUsernameAndBoardId(String loginId, Integer boardId);
    List<Heart> findAllByMemberUsername(String loginId);
}
