package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.repository.BusanTourRepository;

@RestController
public class TagsController {

	@Autowired
	private BusanTourRepository busanTourRepository;

	@GetMapping("/tourtags")
	public ResponseEntity<List<String>> getAllTags() {
        List<String> allTags = busanTourRepository.findDistinctTags();
        return ResponseEntity.ok(allTags);
    }

//    @GetMapping("/alltags")
//    public ResponseEntity<Map<String, TagsDTO>> getAllTags() {
//        Map<String, TagsDTO> tagMaps = new HashMap<>();
//
//        TagsDTO placeTagsDTO = new TagsDTO();
//        placeTagsDTO.setTags(busanPlaceRepository.findAllTags());
//        tagMaps.put("placeTags", placeTagsDTO);
//
//        TagsDTO festivalTagsDTO = new TagsDTO();
//        festivalTagsDTO.setTags(busanFestivalRepository.findAllTags());
//        tagMaps.put("festivalTags", festivalTagsDTO);
//
//        TagsDTO tourTagsDTO = new TagsDTO();
//        tourTagsDTO.setTags(busanTourRepository.findAllTags());
//        tagMaps.put("tourTags", tourTagsDTO);
//
//        return ResponseEntity.ok(tagMaps);
//    }

//    @GetMapping("/alltags")
//    public ResponseEntity<TagsDTO> getAllTags() {
//        TagsDTO dto = new TagsDTO();
//        dto.setTags(busanPlaceRepository.findAllTags());
//        dto.getTags().addAll(busanFestivalRepository.findAllTags());
//        dto.getTags().addAll(busanTourRepository.findAllTags());
//        return ResponseEntity.ok(dto);
//    }
}
