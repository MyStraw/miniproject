package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Beach;
import edu.pnu.service.BeachService;

@RestController
@RequestMapping("/beaches")
public class BeachController {
    private final BeachService beachService;

    @Autowired
    public BeachController(BeachService beachService) {
        this.beachService = beachService;
    }

    @PostMapping
    public Beach addBeachInfo(@RequestBody Beach beach) {
        return beachService.saveBeachInfo(beach);
    }

//    @GetMapping("/beaches")
//    public List<Beach> getBeachInfo(@RequestParam String city1, @RequestParam String city2) {
//    	return beachService.getBeachInfo(city1, city2); 
//         
//    }
//    @GetMapping("/beaches")
//    public List<Beach> getAllBeachInfo() {
//        return beachService.getAllBeachInfo();
//    }
    @GetMapping
    public List<Beach> getBeachInfo(@RequestParam(required = false) String city1, 
                                    @RequestParam(required = false) String city2) {
        if ((city1 == null || city1.isEmpty()) && (city2 == null || city2.isEmpty())) {
            return beachService.getAllBeachInfo();
        } else {
            return beachService.getBeachInfo(city1, city2);
        }
    }

}