package com.example.test.user;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
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
	
    @Value("${upload.path}") // application.properties에서 설정한 경로
    private String uploadPath;
    
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
	public String u_insertOK(UserDTO vo, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		log.info("회원가입 확인");
		log.info("vo:{}", vo);
		
        if (vo.getRegdate() == null) {
            vo.setRegdate(new Date());
        }

	    if (!file.isEmpty()) {
	        // Generate file name
	        String originalFileName = file.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        String fileName = UUID.randomUUID() + extension;

	        // File path
	        String filePath = uploadPath + File.separator + fileName;

	        // Save the file
	        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) {
	            stream.write(file.getBytes());
	        }

	        // Update DTO with new file information
	        vo.setSave_name(fileName);
	        vo.setFile_path("/uploadimg/" + fileName);
	    }

        // DB에 저장
        UserDTO result = service.insertOK(vo);

        if (result != null) {
            return "redirect:/user/info";
        } else {
            return "redirect:/user/info/update";
        }

	}
	@GetMapping("/user/info/update")
	public String info(UserDTO vo, Model model) {
		log.info("회원수정페이지");
		log.info("vo:{}", vo);
		
		String username = (String)session.getAttribute("username");
		String pw = (String)session.getAttribute("pw");
		String gender = (String)session.getAttribute("gender");
		String kakaoID = (String)session.getAttribute("kakaoID");
		Integer age = (Integer)session.getAttribute("age");
		String name = (String)session.getAttribute("name");
		String school = (String)session.getAttribute("school");
		String tel = (String)session.getAttribute("tel");
		String form = (String)session.getAttribute("form");
		String introduce = (String)session.getAttribute("introduce");
		
		
		
		model.addAttribute("title", "회원수정페이지");
		model.addAttribute("username", username);
		model.addAttribute("pw", pw);
		model.addAttribute("gender", gender);
		model.addAttribute("kakaoID", kakaoID);
		model.addAttribute("age", age);
		model.addAttribute("name", name);
		model.addAttribute("school", school);
		model.addAttribute("tel", tel);
		model.addAttribute("form", form);
		model.addAttribute("introduce", introduce);


		model.addAttribute("content", "thymeleaf/user/th_update");
		model.addAttribute("title", "회원수정페이지");
		return "thymeleaf/user/th_update";
	}
	@PostMapping("/user/info/updateOK")
	public String u_updateOK(UserDTO vo, @RequestParam("file") MultipartFile file) throws Exception {
	    String savename = (String) session.getAttribute("savename");

	    // Check if a new file is uploaded
	    if (!file.isEmpty()) {
	        // Generate file name
	        String originalFileName = file.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        String fileName = UUID.randomUUID() + extension;

	        // File path
	        String filePath = uploadPath + File.separator + fileName;

	        // Save the file
	        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) {
	            stream.write(file.getBytes());
	        }

	        // Update DTO with new file information
	        vo.setSave_name(fileName);
	        vo.setFile_path("/uploadimg/" + fileName);
	    } else {
	        // If no new file uploaded, retain the previous image information
	        vo.setSave_name(savename);
	        // Assuming the file path is saved in the UserDTO, set it accordingly
	        // vo.setFile_path(previousFilePath);
	    }

	    // Ensure the regdate is set
	    if (vo.getRegdate() == null) {
	        vo.setRegdate(new Date());
	    }

	    // Update the user information in the database
	    UserDTO result = service.updateOK(vo);

	    if (result != null) {
	        return "redirect:/user/info";
	    } else {
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
			session.setAttribute("kakaoID", vo2.getKakaoID());
			session.setAttribute("age", vo2.getAge());
			session.setAttribute("name", vo2.getRealname());
			session.setAttribute("school", vo2.getSchool());
			session.setAttribute("tel", vo2.getTel());
			session.setAttribute("savename", vo2.getSave_name());
			session.setAttribute("form", vo2.getForm());
			session.setAttribute("introduce", vo2.getIntroduce());
			
			return "redirect:/";
		}
		
		
	}
	@GetMapping("/user/logout")
	public String logout() {
		log.info("로그인 확인");
		
		session.removeAttribute("num");
		session.removeAttribute("username");
		session.removeAttribute("pw");
		session.removeAttribute("gender");
		session.removeAttribute("kakaoID");
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
		String kakaoID = (String)session.getAttribute("kakaoID");
		Integer age = (Integer)session.getAttribute("age");
		String name = (String)session.getAttribute("name");
		String school = (String)session.getAttribute("school");
		String tel = (String)session.getAttribute("tel");
		String savename = (String)session.getAttribute("savename");
		String form = (String)session.getAttribute("form");
		String introduce = (String)session.getAttribute("introduce");
		log.info("savename:{}",savename);
		
		model.addAttribute("title", "회원수정페이지");
		model.addAttribute("username", username);
		model.addAttribute("pw", pw);
		model.addAttribute("gender", gender);
		model.addAttribute("kakaoID", kakaoID);
		model.addAttribute("age", age);
		model.addAttribute("name", name);
		model.addAttribute("school", school);
		model.addAttribute("tel", tel);
		model.addAttribute("savename", savename);
		model.addAttribute("form", form);
		model.addAttribute("introduce", introduce);

		model.addAttribute("content", "thymeleaf/user/th_update");
		model.addAttribute("title", "회원정보페이지");
		
		return "thymeleaf/user/th_selectOne";
	}
}
