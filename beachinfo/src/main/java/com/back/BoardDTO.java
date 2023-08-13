package com.back;
//package edu.pnu.domain.dto;
//
//import java.text.SimpleDateFormat;
//import edu.pnu.domain.Board;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Setter
//@Getter
//public class BoardDTO {
//	private Integer seq;
//	private String username;
//	private String beach;
//	private String content;
//    private String createDate;
//    
//    
//	public BoardDTO(Board board) {
//		
//		this.seq = board.getSeq();
//		this.username = board.getUsername();
//		this.beach = board.getBeach();
//		this.content = board.getContent();
//		
//		if(board.getCreateDate() == null) {
//			createDate = "";
//		} else {
//			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); 		 
//			this.createDate = sdf.format(board.getCreateDate());	
//		}
////		this.imagePath
//
//	}
//}
