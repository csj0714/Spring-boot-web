package com.example.test.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
		log.info("내 정보");
		String username = (String)session.getAttribute("username");
		model.addAttribute("username",username);
		
		return "thymeleaf/user/th_user";
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
			return "redirect:/home";
		} else {
			return "redirect:u_insert";
		}
	}
	@GetMapping("/user/info/update")
	public String info(UserDTO vo, Model model) {
		log.info("회원수정페이지");
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

		if(result != null) {
			return "redirect:/user/info";
		}else {
			return "redirect:/user/info/update";
		}
	}

	@GetMapping("/user/info/delete")
	public String u_delete(UserDTO vo, Model model) {
		log.info("회원삭제페이지");
		
		String username = (String)session.getAttribute("username");
		String name = (String)session.getAttribute("name");

		model.addAttribute("username", username);
		model.addAttribute("name", name);
		
		model.addAttribute("content", "thymeleaf/user/th_delete");
		model.addAttribute("title", "회원삭제페이지");
		
		return "thymeleaf/user/th_delete";
	}

	@Transactional
	@PostMapping("/user/info/deleteOK")
	public String u_deleteOK(String username) {
		log.info("/u_deleteOK...");
		log.info("vo:{}", username);
		
		
		int result = service.deleteOK(username);
		log.info("result:{}", result);

		if(result == 1) {
			return "redirect:/user/logout";
		}else {
			return "redirect:/user/info/delete";
		}

		
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
			session.setAttribute("num", vo2.getNum());
			session.setAttribute("username", vo2.getUsername());
			session.setAttribute("pw", vo2.getPw());
			session.setAttribute("gender", vo2.getGender());
			session.setAttribute("hobby", vo2.getHobby());
			session.setAttribute("age", vo2.getAge());
			session.setAttribute("name", vo2.getName());
			session.setAttribute("school", vo2.getSchool());
			session.setAttribute("tel", vo2.getTel());
			session.setAttribute("savename", vo2.getSave_name());
			
			return "redirect:/";
		}
		
		
	}
	@GetMapping("/user/logout")
	public String logout() {
		log.info("로그인 확인");
		
		session.removeAttribute("username");
		session.removeAttribute("pw");
		session.removeAttribute("gender");
		session.removeAttribute("hobby");
		session.removeAttribute("age");
		session.removeAttribute("name");
		session.removeAttribute("school");
		session.removeAttribute("tel");
		session.removeAttribute("savename");
		
		return "redirect:/";
	}
	@GetMapping("/user/info")
	public String selectOne(Model model) {
		log.info("회원정보페이지");
		
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

		model.addAttribute("content", "thymeleaf/user/th_update");
		model.addAttribute("title", "회원정보페이지");
		
		return "thymeleaf/user/th_selectOne";
	}
}
