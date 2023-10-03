package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.Times;


public interface TimesRepository extends JpaRepository<Times, Integer> {
	@Query("SELECT times.arrive_time " 
			+ "from Times times, Startend startend "
			+ "where startend.seq = times.train_startseq "
			+ "and startend.day_weekend like CONCAT(:day, '%') " 
			+ "and startend.end = :end " 
			+ "and times.station_code = :stationCode ")
	List<String> findArriveTimeByDayAndEndAndStationCode(@Param("day") int day, @Param("end") int end, @Param("stationCode") int stationCode);
	
	
	@Query("SELECT times.arrive_time "
			+ "from Times times, Startend startend "
			+ "where startend.seq = times.train_startseq "
			+ "and startend.day_weekend like CONCAT(:day, '%') "
			+ "and startend.end = 95 "
			+ "and times.station_code = :stationCode ")
	List<String> FromNopo_ToDaDaepo(@Param("day") int day, @Param("stationCode") int stationCode);	
		
	@Query("SELECT times.arrive_time "
			+ "from Times times, Startend startend "
			+ "where startend.seq = times.train_startseq "
			+ "and startend.day_weekend like CONCAT(:day, '%') "
			+ "and startend.end = 239 "
			+ "and times.station_code = :stationCode ")
	List<String> FromJangSan_ToHoPo(@Param("day") int day, @Param("stationCode") int stationCode);	
	
	@Query("SELECT times.arrive_time "
			+ "from Times times, Startend startend "
			+ "where startend.seq = times.train_startseq "
			+ "and startend.day_weekend like CONCAT(:day, '%') "
			+ "and startend.end = 243 "
			+ "and times.station_code = :stationCode ")
	List<String> FromJangSan_ToYangSan(@Param("day") int day, @Param("stationCode") int stationCode);	

	@Query("SELECT times.arrive_time "
			+ "from Times times, Startend startend "
			+ "where startend.seq = times.train_startseq "
			+ "and startend.day_weekend like CONCAT(:day, '%') "
			+ "and startend.end = 218 "
			+ "and times.station_code = :stationCode ")
	List<String> FromYangSan_ToJeonPo(@Param("day") int day, @Param("stationCode") int stationCode);	

	@Query("SELECT times.arrive_time "
			+ "from Times times, Startend startend "
			+ "where startend.seq = times.train_startseq "
			+ "and startend.day_weekend like CONCAT(:day, '%') "
			+ "and startend.end = 201 "
			+ "and times.station_code = :stationCode ")
	List<String> FromYangSan_ToJangSan(@Param("day") int day, @Param("stationCode") int stationCode);	


}
