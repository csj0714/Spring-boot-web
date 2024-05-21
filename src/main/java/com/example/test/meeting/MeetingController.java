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
		
		List<UserDTO> vos = meetingService.selectRandomTwo();
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
		int num = (Integer) session.getAttribute("num");
		
		log.info("username:{}", username);
		log.info("name: {}", name);

		
		
		
		MeetingDTO dto = new MeetingDTO();
		dto.setApplicantNickname(username);
		dto.setApplicantRealName(name);
		dto.setApplicantNum(num);
		dto.setReceiverNickname(vo2.getUsername());
		dto.setReceiverRealName(vo2.getRealname());
		dto.setReceiverNum(vo2.getNum());
		log.info(dto.toString());
		String msg;
		MeetingDTO meeting = meetingService.selectByReceiverAndApplicant(vo2.getNum(), num);
		if(meeting == null) {
			MeetingDTO registeredMeeting  = meetingService.registerMeeting(dto);
			msg = "미팅 신청이 완료되었습니다.";
	        log.info(msg);
	        session.setAttribute("msg", msg);
	        return "redirect:/";
		}else {
			msg = "이미 신청된 미팅입니다.";
	        session.setAttribute("msg", msg);
	        return "redirect:/meeting";
		}
	}
	@GetMapping("/meeting/selectAll")
	public String meetingAll(Model model) {
		log.info("/meeting/selectAll...");
		
		List<MeetingDTO> vos = meetingService.selectAll();
		
		model.addAttribute("vos", vos);
		
		return "thymeleaf/meeting/selectAll";
	}
	@GetMapping("/meeting/selectOne")
	public String meetingOne(Model model) {
		log.info("/meeting/selectOne...");
		
		int num = (Integer) session.getAttribute("num");
		log.info("num:{}", num);
		
		List<MeetingDTO> vos = meetingService.selectOne(num);
		
		model.addAttribute("vos", vos);
		
		return "thymeleaf/meeting/selectOne";
	}
	@GetMapping("/meeting/delete")
	public String delete(MeetingDTO vo, Model model) {
		log.info("/meeting/delete");
		log.info("vo:{}", vo.getApplicantNum());
		log.info("vo:{}", vo.getReceiverNum());
		
		MeetingDTO meeting = meetingService.selectByReceiverAndApplicant(vo.getReceiverNum(), vo.getApplicantNum());
		log.info("vo2:{}", meeting.toString());
		
		model.addAttribute("vo2", meeting);
		
		return "thymeleaf/meeting/delete";
	}
	@PostMapping("/meeting/deleteOK")
	public String deleteOK(@RequestParam("num") int num, Model model) {
		log.info("/meeting/deleteOK");
		log.info("vo:{}", num);
		
		int result = meetingService.deleteByNum(num);
		log.info("result :{}", result);
		if(result == 1) {
			return "redirect:/meeting/selectOne";
		}else {
			return "redirect:/meeting/delete";
		}
	}
}
