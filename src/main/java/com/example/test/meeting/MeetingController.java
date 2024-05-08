package com.example.test.meeting;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.test.user.UserDTO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MeetingController {
	
	@Autowired
	private MeetingService service;
	
	@GetMapping("/meeting")
	public String meeting(Model model) {
		log.info("/meeting...");
		
		List<UserDTO> vos = service.selectRandomTwo();
		log.info("vos :{}", vos.toString());
        
		model.addAttribute("vos", vos);
		
		
		return "thymeleaf/meeting/select";
	}
}
