package edu.pnu.repository.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.jdbc.JdbcBoardRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//실제로 데이터베이스에 접근. //@BeforeEach, @AfterEach 잘 쓰기
//findAll 외에 딴거할땐 주의 해야할것 같다

public class JdbcBoardRepositoryTest {

    @Autowired
    private JdbcBoardRepository jdbcBoardRepo;

    @Test
    public void findAllTest() { 
        List<Board> boards = jdbcBoardRepo.findAll();      
        assertNotNull(boards, "게시글이 비어있음 안돼");
        assertTrue(boards.size() > 0, "게시글이 하나도 없음 안돼");
    }   
}