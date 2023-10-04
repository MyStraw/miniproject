package edu.pnu.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Board;
import edu.pnu.jdbc.JdbcBoardRepository;
import edu.pnu.service.BoardService;


@SpringBootTest //테스트에 필요한 모든 의존성 주입

public class BoardServiceTest {
	
	//Mock : 특정 객체를 흉내내는 짜가. Mock 객체로 대체하여 테스트할때 따로 격리해서 할수있게.
	//Mockito : 프레임워크. 복잡한 의존성 가진 클래스를 mock 객체로 쉽게 만들어주고 관리.
	//-역할 : 특정 객체, 특정 메소드가 호출될시 행동을 미리 정의가능. 쉽게 말하면 
	// 		 어떤 메소드가 호출될때 어떤값을 리턴해야 하는지, 몇번 호출되는지 등등 설정 가능
	//-필요성 : 단위 테스트에서는 가능한 해당 단위 기능만 테스트. 이를 위해서 외부 시스템과의 의존성 제거해야 할때가 많고
	// 		  이때 Mock 객체가 유용
	//단위테스트(실제 로직 검증용), 통합테스트(여러 컴포넌트가 함께 잘 작동하는지 확인)
	
	@InjectMocks //mock 객체 주입
	private BoardService boardService;

	@Mock //mock 객체 생성
	private JdbcBoardRepository boardRepo;

	@BeforeEach //테스트 실행전에 실행
	public void init() {
		MockitoAnnotations.openMocks(this); //@Mock, @InjectMocks 어노테이션 초기화
	}

    @Test
    public void testList() {
    	//2개 생성 해주고
        Board board1 = new Board();
        Board board2 = new Board();
        when(boardRepo.findAll()).thenReturn(Arrays.asList(board1, board2));
        //findAll호출시 위에 생성한 2개 Board객체를 리스트로 리턴
        List<Board> result = boardService.list(); //진짜 boardService안에 list()호출해서 result에 저장
        assertNotNull(result); //null인지 확인
        assertEquals(2, result.size()); //크기 2개인지 확인
        verify(boardRepo, times(1)).findAll(); //정확히 한번만 호출됐는지 확인
    }
}