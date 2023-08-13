package edu.pnu.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Beach;
import edu.pnu.persistence.BeachRepository;

@Service
public class BeachService {
    private final BeachRepository beachRepository;

    @Autowired
    public BeachService(BeachRepository beachRepository) {
        this.beachRepository = beachRepository;
    }

    public Beach saveBeachInfo(Beach beach) {
        return beachRepository.save(beach);
    }

    public List<Beach> getBeachInfo(String city1, String city2) {
        return beachRepository.findByCity1AndCity2(city1, city2);
    }
    
    public List<Beach> getAllBeachInfo() {
        return beachRepository.findAll();
    }

}