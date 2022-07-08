package com.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
class MapperTests {

	@Autowired
	private BoardMapper boardMapper;

	@Test
	public void testOfInsert() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목");
		params.setContent("1번 게시글 내용");
		params.setWriter("테스터");

		int result = boardMapper.insertBoard(params);
		System.out.println("결과는 " + result + "입니다.");
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			//String boardJson = new ObjectMapper().writeValueAsString(board);
            String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

			System.out.println("=========================");
			System.out.println(boardJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		//given when then
		//BoardDTO의 데이터를 다른값으로 수정 하는 mapper의 updateBoard가 잘 동작하는지
		
		//1. given : 데이터 준비
		BoardDTO params = new BoardDTO();
		//idx가 1인 데이터를 바꿀거다.
		params.setIdx((long)1);
		params.setTitle("테스트함수에서 바꾼 제목");
		params.setContent("테스트함수에서 바꾼 내용");
		params.setWriter("테스터2");
		
		//2. when : 준비된 데이터로 실행
		boardMapper.updateBoard(params);
	}
	
	@Test
	public void testMultipleInsert() {
		for(int i=2; i<=50; i++) {
			BoardDTO params = new BoardDTO();
			params.setIdx((long)i);
			params.setTitle(i+"번 제목");
			params.setContent(i+"번 내용");
			params.setWriter(i+"테스터");
			boardMapper.insertBoard(params);
			
		}
	}
	

}