package edu.pnu.controller.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.pnu.service.BoardService;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {
	//MockMVC : 스프링 MVC 애플리케이션 테스트 위한 프레임워크. HTTP요청을 Dispatcher Servlet에 전송하고 그 결과 받을수있다
	//컨트롤러 동작 검증하는데 유용. 컨트롤러에서 주로 쓰넹
	
	//HTTP요청 메소드(Get, post, put, delete 등등) 설정
	//리퀘스트 헤더, 파람, 바디 등을 설정가능
	//리스폰스 코드, 헤더, 바디 등을 검증
	
	//독립성 : 실제 HTTP서버를 실행하지 않고도 springMVC 동작 테스트 가능. 빠른테스트, 격리된 환경에서 가능
	//정확성 : 리퀘스트랑 리스폰스를 상세하게 설정, 검증가능.
	//편의성 : 다양한 검증 메소드 제공. 테스트 코드작성 편리해짐.
	
	

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BoardService boardService;

//	@Test
//	public void createTest() throws Exception {
//		int mockStationCode = 1;
//		String mockAuthHeader = "Bearer asderqweropiuzxcqwerasdf";
//		String mockBoardStr = "{\"title\": \"냐하하\", \"content\": \"뇨호호\"}";
//		// MultipartFile mockImage = new MockMultipartFile("image", "original_name",
//		// "image/jpeg", "image_file_here".getBytes());
//
//		Board mockBoard = new Board(); // Mock Board 객체
//		mockBoard.setTitle("테스트 제목");
//		mockBoard.setContent("테스트 내용");
//
//		// Mocking 설정
//		when(boardService.create(mockStationCode, mockAuthHeader, mockBoardStr, null)).thenReturn(mockBoard);
//
//		mockMvc.perform(
//				MockMvcRequestBuilders.post("/board/create/{stationcode}", 1).header("Authorization", mockAuthHeader)
//						// .param("image", "image_file_here")
//						.param("board", mockBoardStr))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
	@Test
	public void listTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/board/list")) //mockMvc.perform()으로 get 요청을 보냄. 
		.andExpect(MockMvcResultMatchers.status().isOk()); //andExpect() 메소드는 리스폰스로 200인지 검증함.
	}
	
	@Test
	public void listStationCodeTest() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/board/list/{stationcode}", 1))
	        .andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void findTest() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/board/find/{id}", 1))
	        .andExpect(MockMvcResultMatchers.status().isOk());
	}
//	@Test
//	public void updateTest() throws Exception {
//		Board mockBoard = new Board();  // 이 객체는 실제로는 데이터베이스 또는 다른 로직에서 반환되는 객체를 모방
//	    mockBoard.setId(1);
//	    mockBoard.setTitle("테스트 Title");
//	    mockBoard.setContent("테스트 Content");
//	    
//	    // Mocking 설정
//	    when(boardService.update(1, mockBoard)).thenReturn(mockBoard);
//	  
//	    mockMvc.perform(MockMvcRequestBuilders.put("/board/update/{id}", 1)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .content("{제이슨}"))
//	        .andExpect(MockMvcResultMatchers.status().isOk());
//	}
//	@Test
//	public void deleteTest() throws Exception {
//	    mockMvc.perform(MockMvcRequestBuilders.delete("/board/delete/{id}", 1))
//	        .andExpect(MockMvcResultMatchers.status().isOk());
//	}
}
