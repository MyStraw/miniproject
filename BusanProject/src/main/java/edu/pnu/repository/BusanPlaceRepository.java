package edu.pnu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.pnu.entity.BusanPlace;

@Repository
public interface BusanPlaceRepository extends JpaRepository<BusanPlace, Integer> {

	Optional<BusanPlace> findById(Integer id);
	
	@Query("SELECT p.tags FROM BusanPlace p WHERE p.tags IS NOT NULL")
    List<String> findAllTags();
    
}
