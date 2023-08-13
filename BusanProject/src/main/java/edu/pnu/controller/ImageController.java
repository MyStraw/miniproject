package edu.pnu.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @GetMapping(value = "/image", produces = "image/jpeg")
    public byte[] getImage() throws IOException {
        // 이미지 파일 경로 설정
        String imagePath = "path_to_your_image/image.jpg"; // 이미지 파일의 실제 경로로 수정해야 합니다.
        
        // 이미지 파일을 바이트 배열로 읽어옴
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        
        return imageBytes;
    }
}
