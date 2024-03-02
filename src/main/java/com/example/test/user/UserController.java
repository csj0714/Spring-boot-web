package com.example.test.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/user")
	public String user(Model model) {
		log.info("마이페이지");
		
		return "thymeleaf/user/th_layout_main";
	}
	@GetMapping("/user/insert")
	public String u_insert(Model model) {
		log.info("회원가입페이지");
		
		model.addAttribute("content", "thymeleaf/user/th_insert");
		model.addAttribute("title", "회원가입페이지");
		return "thymeleaf/user/th_insert";
	}
	@PostMapping("/user/insertOK")
	public String u_insertOK(UserDTO vo) {
		log.info("회원가입 확인");
		log.info("vo:{}", vo);

		UserDTO result = service.insertOK(vo);
		log.info("result:{}", result);

		if (result != null) {
			return "redirect:home";
		} else {
			return "redirect:u_insert";
		}
	}
	@GetMapping("/user/info")
	public String u_selectOne(UserDTO vo, Model model) {
		log.info("회원정보페이지");
		log.info("vo:{}", vo);

//		UserDTO vo2 = service.selectOne(vo);
		UserDTO vo2 = new UserDTO();
		vo2.setNum(1);
		vo2.setAge(11);
		vo2.setGender("남자");
		vo2.setHoddy("취미");
		vo2.setUsername("아이디");
		vo2.setName("이름");
		vo2.setPw("패스워드");
		vo2.setSave_name("프로필이미지");
		vo2.setSchool("학교");
		vo2.setTel("전화");
		

		model.addAttribute("content", "thymeleaf/user/th_selectOne");
		model.addAttribute("title", "회원정보페이지");
		return "thymeleaf/user/th_selectOne";
	}
	@GetMapping("/user/info/update")
	public String u_update(Model model) {
		log.info("회원수정페이지");
		
		model.addAttribute("content", "thymeleaf/user/th_update");
		model.addAttribute("title", "회원수정페이지");
		return "thymeleaf/user/th_update";
	}
	@PostMapping("/user/info/updateOK")
	public String u_updateOK(UserDTO vo) {

		//수정일자 반영안하면 null값이 들어가는 것을 방지하기위해...
		if(vo.getRegdate()==null) {
			vo.setRegdate(new Date());
		}
		
		UserDTO result = service.updateOK(vo);
		log.info("result:{}", result);

		return "redirect:m_selectOne?num=" + vo.getNum();
	}

	@GetMapping("/user/info/delete")
	public String u_deleteOK(UserDTO vo, Model model) {
		log.info("회원삭제페이지");
		
		model.addAttribute("content", "thymeleaf/user/th_delete");
		model.addAttribute("title", "회원삭제페이지");
		
		return "thymeleaf/user/th_delete";
	}
	@Transactional
	@PostMapping("/user/info/deleteOK")
	public String u_deleteOK(UserDTO vo) {
		log.info("/m_deleteOK...");
		log.info("vo:{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);

		return "redirect:m_selectAll";
	}
	@GetMapping("/user/login")
	public String login(Model model) {
		log.info("로그인페이지");
			
		model.addAttribute("content", "thymeleaf/user/th_login");
		model.addAttribute("title", "로그인페이지");
		
		return "thymeleaf/user/th_login";
	}
	
	@PostMapping("/user/loginOK")
	public String loginOK(UserDTO vo, Model model) {
		log.info("로그인 확인");
		log.info("vo:{}",vo);
		UserDTO vo2 = service.loginOK(vo);
		
		if(vo2 == null) {
			return "redirect:login";
		}
		else {
			session.setAttribute("username", vo2.getUsername());
			return "redirect:/";
		}
		
		
	}
	@GetMapping("/user/logout")
	public String logout() {
		log.info("로그인 확인");
		
		session.removeAttribute("username");
		
		return "redirect:/";
	}
}
