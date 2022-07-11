package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) { // idx가 없으면 껍데기 dto생성
			model.addAttribute("board", new BoardDTO());
		} else {	// idx가 있으면 해당 idx로 dto가져오기
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null) {	//idx로 조회했는데 가져온 dto가 null이면
				return "redirect:/board/list.do";	//글쓰기 화면이 아닌 목록 화면으로 이동
			}
			model.addAttribute("board", board);
		}
		return "board/write";
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/board/list.do";
	}
}