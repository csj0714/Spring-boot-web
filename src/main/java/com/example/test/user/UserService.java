package com.example.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HttpSession session;

	public UserDTO insertOK(UserDTO vo) {
		return userRepo.save(vo);
	}

	public UserDTO selectOne(UserDTO vo) {
		return userRepo.findByNum(vo.getNum());
	}

	public UserDTO updateOK(UserDTO vo) {
	    UserDTO existingUser = userRepo.findByUsername(vo.getUsername());
	    if (existingUser != null) {
	        // existingUser가 null이 아닌 경우에만 업데이트를 수행
	        existingUser.setName(vo.getName());
	        existingUser.setPw(vo.getPw());
	        existingUser.setTel(vo.getTel());
	        existingUser.setGender(vo.getGender());
	        existingUser.setHobby(vo.getHobby());
	        existingUser.setSchool(vo.getSchool());
	        existingUser.setTel(vo.getTel());
	        
	        updateSession(existingUser);
	        return userRepo.save(existingUser);
	    } else {
	        // existingUser가 null인 경우에 대한 처리
	        log.error("User with username {} not found", vo.getUsername());
	        // 예외 처리 또는 새로운 유저 생성 등을 여기에 추가할 수 있습니다.
	        return null; // 예시로 null을 반환하도록 하였습니다. 실제로는 예외 처리 등을 하셔야 합니다.
	    }
	}
	private void updateSession(UserDTO user) {
	    // 세션에 사용자 정보 업데이트
	    session.setAttribute("username", user.getUsername());
	    session.setAttribute("pw", user.getPw());
	    session.setAttribute("gender", user.getGender());
	    session.setAttribute("hobby", user.getHobby());
	    session.setAttribute("age", user.getAge());
	    session.setAttribute("name", user.getName());
	    session.setAttribute("school", user.getSchool());
	    session.setAttribute("tel", user.getTel());
	    session.setAttribute("savename", user.getSave_name());
	}
	public int deleteOK(UserDTO vo) {
		return userRepo.deleteByNum(vo.getNum());
	}
	public UserDTO loginOK(UserDTO vo) {
		return userRepo.findByUsernameAndPw(vo.getUsername(),vo.getPw());
	}

	

}
