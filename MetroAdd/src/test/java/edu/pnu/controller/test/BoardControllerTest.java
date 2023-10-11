package edu.pnu.controller.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.annotation.Validated;

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
	
	@Test //min 1, max 500으로 해놨다. stationcode를. 0으로 주면 어떤일 벌어지냐
	public void listStationCodeTest_InvalidCase1() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/board/list/{stationcode}", 0))
	        .andExpect(MockMvcResultMatchers.status().isBadRequest()); // 400 에러가 뜬다
	}
	
	@Test //이건 500 넘긴 숫자로. 
	public void listStationCodeTest_InvalidCase2() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/board/list/{stationcode}", 501))
	        .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
	}
	
//	400 Bad Request
//	- 요청이 서버로 제대로 전달되지 않았을 때 발생
//	- 클라이언트측 요청이 잘못된 문법이거나, 빠진 필수 정보 등으로 인해 서버가 요청을 이해할수 없을때 이 에러가 반환.
	
//	401 Unauthorized
//	- 인증이 필요한 자원에 대해 인증하지 않고 요청을 했을 때 발생.
//	- 일반적으로 이 에러 코드는 유효한 자격 증명이 없는 경우에 생긴다.
	
//	402 Payment Required - 이건 아직?? 
	
//	403 Forbidden
//	- 서버가 요청을 이해는 했지만, 해당 요청을 실행할 권한이 없을 때 발생.
//	- 401과 다르게, 클라이언트의 자격 증명이 있더라도 접근이 금지된 리소스를 요청했을때 발생.
	
//	404 Not Found
//	- 요청한 리소스가 서버에 없을 때 반생하는 에러.
//	- URL이 잘못되었거나, 해당 리소스가 삭제되었을 때 일반적으로 발생.	
	
}
