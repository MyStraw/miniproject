package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Facilities;
import edu.pnu.domain.Startend;
import edu.pnu.domain.Station;
import edu.pnu.domain.Times;
import edu.pnu.persistence.FacilitiesRepository;
import edu.pnu.persistence.StartendRepository;
import edu.pnu.persistence.StationRepository;
import edu.pnu.persistence.TimesRepository;

@RestController
public class MetroController {

	@Autowired
	StationRepository stationRepo;

	@Autowired
	FacilitiesRepository facilitiesRepo;

	@Autowired
	StartendRepository startendRepo;

	@Autowired
	TimesRepository timesRepo;

//  @CrossOrigin(origins = "http://localhost:3000") 이거 한개만 연결 허용해줄라면. 이거 대신 cors 썼지.
	@GetMapping("/station/{stationcode}")
	public List<Station> getStation(@PathVariable int stationcode) {
		return stationRepo.findBystationcode(stationcode);
	}

	@GetMapping("/facilities/{stationcode}")
	public List<Facilities> getFacilities(@PathVariable int stationcode) {
		return facilitiesRepo.findBystationcode(stationcode);
	}

	@GetMapping("/startend")
	public List<Startend> getStartend() {
		return startendRepo.findAll();
	}

	@GetMapping("/times")
	public List<Times> getTimes() {
		return timesRepo.findAll();
	}

	@GetMapping("/station/{day}/{end}/{stationcode}")
	public List<String> getArriveTimes(@PathVariable int day, @PathVariable int end, @PathVariable int stationcode) {
		List<String> result = timesRepo.findArriveTimeByDayAndEndAndStationCode(day, end, stationcode); 
																										
		if (end == 101) {
			result.addAll(timesRepo.FromNopo_ToDaDaepo(day, stationcode));
		}
		if (end == 209) {
			result.addAll(timesRepo.FromJangSan_ToHoPo(day, stationcode));
			result.addAll(timesRepo.FromJangSan_ToYangSan(day, stationcode));
		}

		if (end == 239) {
			result.addAll(timesRepo.FromJangSan_ToYangSan(day, stationcode));
		}

		if (end == 218) {
			result.addAll(timesRepo.FromYangSan_ToJangSan(day, stationcode));
		}

		return result;
	}

}