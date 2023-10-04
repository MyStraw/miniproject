package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.Facilities;
import edu.pnu.domain.Station;

public interface FacilitiesRepository extends JpaRepository<Facilities, Integer> {
	
	List<Facilities> findBystationcode(int stationcode);

}
