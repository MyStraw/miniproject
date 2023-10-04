package edu.pnu.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.pnu.response.FileResponse;
import edu.pnu.service.ImageService;


@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(@PathVariable Integer boardId, @RequestParam("file") MultipartFile file) throws Exception {
        FileResponse response = imageService.uploadFile(boardId, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(String fileName) throws IOException {
        return imageService.getImage(fileName);
    }
}
