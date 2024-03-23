package com.example.test.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.user.UserDTO;


import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/board")
	public String board(@RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5") int pageBlock, Model model) {
		log.info("게시판");
		
		log.info("cpage : {}, pageBlock : {}", cpage, pageBlock);

//		List<MemberVO_JPA> vos = service.selectAll();
		List<BoardDTO> vos = service.selectAllPageBlock(cpage, pageBlock);

		model.addAttribute("vos", vos);
		
		// member테이블에 들어있는 모든회원수는 몇명?
		long total_rows = service.getTotalRows();
		log.info("total_rows:" + total_rows);

		long totalPageCount = 1;
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		// 페이지 링크 몇개?
		log.info("totalPageCount:" + totalPageCount);
		model.addAttribute("totalPageCount", totalPageCount);
//		model.addAttribute("totalPageCount", 10);//테스트용

		model.addAttribute("content", "thymeleaf/board/th_board");
		model.addAttribute("title", "게시판목록");

		return "thymeleaf/board/th_board";
	}
	@GetMapping("/board/insert")
	public String b_insert(Model model) {
		log.info("게시판생성페이지");
		
		model.addAttribute("content", "thymeleaf/board/th_insert");
		model.addAttribute("title", "게시판생성페이지");
		return "thymeleaf/board/th_insert";
	}
	@GetMapping("/board/myBoard")
	public String myBoard(UserDTO vo, Model model) {
		log.info("회원정보페이지");
		log.info("vo:{}", vo);
		
		String username = (String)session.getAttribute("username");
		String pw = (String)session.getAttribute("pw");
		String gender = (String)session.getAttribute("gender");
		String hobby = (String)session.getAttribute("hobby");
		Integer age = (Integer)session.getAttribute("age");
		String name = (String)session.getAttribute("name");
		String school = (String)session.getAttribute("school");
		String tel = (String)session.getAttribute("tel");
		
		model.addAttribute("title", "회원수정페이지");
		model.addAttribute("username", username);
		model.addAttribute("pw", pw);
		model.addAttribute("gender", gender);
		model.addAttribute("hobby", hobby);
		model.addAttribute("age", age);
		model.addAttribute("name", name);
		model.addAttribute("school", school);
		model.addAttribute("tel", tel);

		model.addAttribute("content", "thymeleaf/board/th_update");
		model.addAttribute("title", "회원정보페이지");
		return "thymeleaf/board/th_update";
	}
	@GetMapping("/user/myBoard/delete")
	public String b_delete(UserDTO vo, Model model) {
		log.info("회원삭제페이지");
		
		String username = (String)session.getAttribute("username");

		model.addAttribute("username", username);
		
		model.addAttribute("content", "thymeleaf/board/th_delete");
		model.addAttribute("title", "회원삭제페이지");
		
		return "thymeleaf/board/th_delete";
	}
}
