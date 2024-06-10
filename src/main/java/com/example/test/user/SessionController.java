package com.example.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
public class SessionController {

	@Autowired
	private HttpSession session;

    @GetMapping("/getSessionInfo")
    public ResponseEntity<String> getSessionInfo() {
    	
        String username = (String) session.getAttribute("username");
        String gender = (String) session.getAttribute("gender");
        log.info("username:{}, gender:{}", username, gender);
        // 세션 정보 사용
        return ResponseEntity.ok("Username: " + username + ", Gender: " + gender);
    }
}
