package edu.pnu.controller;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import edu.pnu.domain.Board;
import edu.pnu.service.BoardService;

@RestController
@RequestMapping("/Boards")
public class BoardController {

	@Value("${spring.servlet.multipart.location}")
	private String location;


	private String saveImage(MultipartFile image) {
		try {
	        if (image == null || image.isEmpty()) {
	            return null;
	        }

	        byte[] bytes = image.getBytes();
	        Path path = Paths.get(location + image.getOriginalFilename());
	        Files.write(path, bytes);

	        return path.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Autowired
	private BoardService boardService;

	@GetMapping
	public List<Board> getAllBoardList() {
		return boardService.getAllBoardList();
	}

//    @PostMapping
//    public void createtBoard(@RequestBody Board board) {
//        boardService.createBoard(board);
//    }

	@PostMapping
	public ResponseEntity<?> createBoard(@RequestParam("username") String username, @RequestParam("beach") String beach, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam(value = "image", required = false) MultipartFile image) {

		Board board = new Board();
		board.setUsername(username);
		board.setBeach(beach);
		board.setTitle(title);
		board.setContent(content);

		String imagePath = saveImage(image);
		board.setImagePath(imagePath);

		boardService.createBoard(board);
		return ResponseEntity.ok("Board and Image uploaded successfully!");
	}

//    @GetMapping("/{username}")
//    public Board getBoardByUsername(@AuthenticationPrincipal String username) {
//        return boardService.getBoardByUsername(username);
//    }
//
	  @PutMapping("/{seq}")
	    public ResponseEntity<?> updateBoard(@PathVariable Integer seq, 
	    	    @RequestParam("title") String title,
	    	    @RequestParam("content") String content,
	    	    @RequestParam(value = "image", required = false) MultipartFile image
) {
	        Optional<Board> board = boardService.getBoardBySeq(seq);
	        if (!board.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }

	        Board findBoard = board.get();
	        findBoard.setContent(content);
	        findBoard.setTitle(title);
	        if (image != null && !image.isEmpty()) {
	            String imagePath = saveImage(image); // You need to implement this method to store the file and get the path
	            findBoard.setImagePath(imagePath);
	        }
	        boardService.updateBoard(findBoard);

	        return ResponseEntity.noContent().build();
	    }
	
	@GetMapping("/{seq}")
    public ResponseEntity<Board> getBoardBySeq(@PathVariable Integer seq) {
        return boardService.getBoardBySeq(seq)
            .map(board -> ResponseEntity.ok(board))
            .orElse(ResponseEntity.notFound().build());
    }
	
	@GetMapping("/{seq}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable Integer seq) {
		try {
	        Optional<Board> optionalBoard = boardService.getBoardBySeq(seq);
	        
	        if (!optionalBoard.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }

	        Board board = optionalBoard.get();

	        if (board.getImagePath() == null) {
	            return ResponseEntity.notFound().build();
	        }
	        
	        Path path = Paths.get(board.getImagePath());
	        byte[] image = Files.readAllBytes(path);

	        // Determining the content type based on the file's extension
	        String contentType = Files.probeContentType(path);

	        return ResponseEntity.ok()
	                             .contentType(MediaType.parseMediaType(contentType))
	                             .body(image);
	    } catch (NoSuchFileException nsfe) {
	        return ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	

//	@DeleteMapping("/{seq}")
//	public void deleteBoard(@PathVariable Integer seq, @AuthenticationPrincipal Board board) {
//		boardService.deleteBoard(seq);
//	}

	@DeleteMapping("/{seq}")
	public void deleteBoard(@PathVariable Integer seq) {
	    boardService.deleteBoard(seq);
	}
	private boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@DeleteMapping
	public void deleteAllBoards(@AuthenticationPrincipal String username) {
		// Check if user is admin
		if (isAdmin()) {
			boardService.deleteAllBoards();
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete all boards");
		}
	}
}