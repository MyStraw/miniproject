package edu.pnu.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class FileResponse {
	private String status;
	private String message;
}

@Slf4j
@RestController
public class ImageController {
	
	
//	 @Autowired
//    private BoardRepository boardRepository;		

	@Value("${spring.servlet.multipart.location}")
	private String location;

	@PostMapping("/api/upload") //특정 게시물에 이미지 파일 넣기
	public ResponseEntity<?> uploadFile(@PathVariable Integer boardId, @RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return ResponseEntity.ok(new FileResponse("OK", "File is Empty!"));
		}
		try {
			// File의 메소드를 이용하는 방법
			file.transferTo(new File(location + file.getOriginalFilename()));	
			// FileOutputStream 객체를 이용하는 방법
			// FileOutputStream fos = new FileOutputStream(location +
			// file.getOriginalFilename());
			// fos.write(file.getBytes());
			// fos.close();
			
//			Board board = boardRepository.findById(boardId).orElse(null);
//			 if (board == null) {
//		            return ResponseEntity.ok(new FileResponse("Error", "게시물을 찾을 수 없습니다."));
//		        }
			//Board board = new Board();
//            board.setImage(file.getBytes());
//            boardRepository.save(board);
            
		} catch (Exception e) {
			return ResponseEntity.ok(new FileResponse("Exception", e.getMessage()));
		}
		return ResponseEntity.ok(new FileResponse("OK", "업로드되었습니다."));
	}
	
	@GetMapping("/display") //내 로컬의 이미지를 표시하기
	public ResponseEntity<byte[]> getImage(String fileName){
		log.info("getImage()......." + fileName);		
		File file = new File("C:/temp/uploads/" + fileName);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-type",Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;		
	}
}
