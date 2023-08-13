package edu.pnu.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.entity.BusanComment;
import edu.pnu.repository.BusanCommentRepository;
import edu.pnu.repository.BusanUserRepository;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private BusanCommentRepository busanCommentRepository;
	@Autowired
	private BusanUserRepository busanUserRepository;

	@GetMapping("/{type}/{typeid}") // ex) /food/25
	public ResponseEntity<?> getCommentByTypeAndPostId(@PathVariable String type, @PathVariable Integer typeid) {
		List<BusanComment> comment = busanCommentRepository.findByTypeAndTypeId(type, typeid);
		if (comment != null) {
			return ResponseEntity.ok(comment);
		}
		return ResponseEntity.ok(null);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addComment(@RequestBody BusanComment comment) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String authUsername = auth.getName();
		comment.setUsername(authUsername);
		comment.setCreatedAt(new Date());
		busanCommentRepository.save(comment);
		return ResponseEntity.ok("Comment added successfully");
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<String> editComment(@PathVariable Integer id, @RequestBody BusanComment updatedComment) {
		Optional<BusanComment> ecopt = busanCommentRepository.findById(id);

		if (ecopt.isPresent()) {
			BusanComment existingComment = ecopt.get();
			existingComment.setComment(updatedComment.getComment());
			busanCommentRepository.save(existingComment);
			return ResponseEntity.ok("Comment updated successfully");
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
		BusanComment commentToDelete = busanCommentRepository.findById(id).orElse(null);
		if (commentToDelete != null) {
			busanCommentRepository.delete(commentToDelete);
			return ResponseEntity.ok("Comment deleted successfully");
		}
		return ResponseEntity.notFound().build();
	}
}
