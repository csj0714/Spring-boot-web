package com.example.test;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	@GetMapping({"/","/home"})
	public String home(HttpSession session, Model model) {
		log.info("/home...");
		
		
		Date to_day = new Date();//Wed Feb 21 16:35:13 KST 2024
		model.addAttribute("to_day",to_day);
		
        // 세션에서 사용자 이름 가져오기
        String username = (String) session.getAttribute("username");
        log.info(username);
        // 모델에 사용자 이름 추가
        model.addAttribute("username", username);
		
		
		
		return "thymeleaf/th_home";
	}
}
