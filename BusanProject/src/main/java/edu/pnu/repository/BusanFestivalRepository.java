package edu.pnu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.pnu.entity.BusanFestival;

@Repository
public interface BusanFestivalRepository extends JpaRepository<BusanFestival, Integer> {

	Optional<BusanFestival> findById(Integer id);

	@Query("SELECT f FROM BusanFestival f WHERE f.startDate >= CURRENT_DATE ORDER BY f.startDate ASC")
	List<BusanFestival> findUpcomingFestivals(Pageable pageable);

	@Query("SELECT DISTINCT p.tags FROM BusanFestival p WHERE p.tags IS NOT NULL")
	List<String> findAllTags();

}
