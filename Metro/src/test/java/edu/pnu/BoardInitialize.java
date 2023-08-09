package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;
@SpringBootTest
public class BoardInitialize {

	@Autowired
	BoardRepository boardRepo;

	@Test
	public void doWork() {
		for(int i = 1 ; i<=20 ; i++) {
		boardRepo.save(Board.builder()
				.title("냐하하"+i)
				.content("뇨호호호호호"+i)
				.author("MEMBER"+i)
				.stationcode(400+i/4)
				.build());
		}
	}

}