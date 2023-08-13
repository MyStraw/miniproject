package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.service.BoardService;

@SpringBootTest
class BeachinfoApplicationTests {
//	@Autowired
//	private MemberRepository memRepo;
//
//	
//	@Autowired
//	private BCryptPasswordEncoder encoder;
	
	
	
//	@Test
//	void contextLoads() {
////		memRepo.save(Member.builder().username("member").password(encoder.encode("abcd")).role("ROLE_MEMBER").build());
//		System.out.println("Test");
//	}
	
	
	@Autowired
    private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepo;


    @Test
    public void testCreateBoard() {
    	
        Board board = new Board();
        board.setUsername("lee");
        board.setBeach("가계");
        board.setContent("테스트 입력 예시");
        boardRepo.save(board);

    }

}
