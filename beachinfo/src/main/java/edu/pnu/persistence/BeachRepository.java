package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Beach;


public interface BeachRepository extends JpaRepository<Beach, Integer> {
	List<Beach> findByCity1AndCity2(String city1, String city2);
}
