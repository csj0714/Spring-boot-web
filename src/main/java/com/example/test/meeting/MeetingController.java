package com.example.test.meeting;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.test.user.UserDTO;
import com.example.test.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MeetingController {
	
	@Autowired
	private MeetingService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/meeting")
	public String meeting(Model model) {
		log.info("/meeting...");
		
		List<UserDTO> vos = service.selectRandomTwo();
		log.info("vos :{}", vos.toString());
        
		model.addAttribute("vos", vos);
		
		
		return "thymeleaf/meeting/select";
	}
	
	@GetMapping("/meeting/register")
	public String register(UserDTO vo, Model model) {
		log.info("/register...");
		
		UserDTO vo2 = userService.selectOne(vo);
		
		model.addAttribute("vo2", vo2);
		
		return "thymeleaf/meeting/register";
	}
	
	@PostMapping("/meeting/registerOK")
	public String registerOK(UserDTO vo, Model model) {
		log.info("/registerOK...");
		
		log.info("vo:{}",vo.toString());
		UserDTO vo2 = userService.selectOne(vo);
		log.info("vo2 :{}", vo2.toString());
		String username = (String) session.getAttribute("username");
		String name = (String) session.getAttribute("name");
		
		log.info("username:{}", username);
		log.info("name: {}", name);

		
		
		
		MeetingDTO dto = new MeetingDTO();
		dto.setUsername(vo2.getUsername());
		dto.setRealname(vo2.getRealname());
		dto.setSelect_username(username);
		dto.setSelect_realname(name);
		log.info(dto.toString());
		
		MeetingDTO registeredMeeting  = service.registerMeeting(dto);
		
		
		
		if(registeredMeeting  != null) {
			return "redirect:/";
		}else {
			return "redirect:/meeting";
		}
	}
	
}
