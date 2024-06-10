package com.example.test.meeting;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.user.UserDTO;
import com.example.test.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MeetingController {
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/meeting")
	public String meeting(Model model) {
		log.info("/meeting...");
		
		int num = (int) session.getAttribute("num");
		
		log.info("num:{}", num);
		
		UserDTO loginUser = new UserDTO();
		loginUser.setNum(num);
		
		UserDTO currentUser = userService.selectOne(loginUser);
		
		List<MeetingDTO> vos = meetingService.ramdomMeetingTwo(currentUser);
		log.info("vos :{}", vos.toString());
        
		model.addAttribute("vos", vos);
		
		
		return "thymeleaf/meeting/select";
	}
	
	@GetMapping("/meeting/insert")
	public String insert(UserDTO vo, Model model) {
		log.info("/register...");
		
		UserDTO vo2 = userService.selectOne(vo);
		String username = (String)session.getAttribute("username");
		int num = (int)session.getAttribute("num");
		
		model.addAttribute("username", username);
		model.addAttribute("num", num);
		
		model.addAttribute("vo2", vo2);
		
		return "thymeleaf/meeting/insert";
	}

	@PostMapping("/meeting/insertOK")
	public String insertOK(MeetingDTO vo, Model model) {
		log.info("/register...");
		
		MeetingDTO vo2 = meetingService.insertOK(vo);
		
		model.addAttribute("vo2", vo2);
		
		if(vo2 != null) {
			return "redirect:/menu";
		}else {
			return "redirect:/meeting/delete";
		}
	}
	@GetMapping("/meeting/register")
	public String register(MeetingDTO vo, Model model) {
		log.info("/register...");
		
		int meetingNum = vo.getNum();
		int organizerNum = vo.getOrganizerNum();
		
		
		model.addAttribute("meetingNum", meetingNum);
		model.addAttribute("organizerNum", organizerNum);
		
		return "thymeleaf/meeting/register";
	}
	@PostMapping("/meeting/registerOK")
	public String registerOK(MeetingDTO dto, UserDTO vo, Model model) {
		log.info("/registerOK...");
		log.info("vo:{}", vo.toString());
		
		MeetingRegister register = new MeetingRegister();
		register.setMeetingNum(dto.getNum());
		register.setUserNum(vo.getNum());
		register.setStatus("신청중");
		log.info(register.toString());
		MeetingRegister result = meetingService.registerMeeting(register);
		
		model.addAttribute("result", result);
		
		if(result != null) {
			return "redirect:/";
		}else {
			return "redirect:/meeting/register";
		}
	}
}
