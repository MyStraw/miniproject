package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {
	
//	@Query("SELECT * " 
//			+ "from Station station "
//			+ "where station.station_code = :stationCode ")
	List<Station> findBystationcode(int stationcode);
}
