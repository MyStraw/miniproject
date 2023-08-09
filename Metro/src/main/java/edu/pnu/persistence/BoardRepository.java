package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	
//	@Query("SELECT * " 
//	+ "from Board board "
//	+ "where board.station_code = :stationcode ")
	List<Board> findBystationcode(int stationcode);
}
