package edu.pnu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.pnu.response.FileResponse;

@Service
public class ImageService {

    @Value("${spring.servlet.multipart.location}")
    private String location;

    public FileResponse uploadFile(Integer boardId, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new FileResponse("OK", "File is Empty!");
        }

        file.transferTo(new File(location + file.getOriginalFilename()));
        return new FileResponse("OK", "업로드되었습니다.");
    }

    public ResponseEntity<byte[]> getImage(String fileName) throws IOException {
        File file = new File("C:/temp/uploads/" + fileName);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", Files.probeContentType(file.toPath()));
        return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
    }
}
