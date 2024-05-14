package com.example.test.meeting;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.user.UserDTO;
import com.example.test.user.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetingService {
	
	@Autowired
	private MeetingRepository meetingRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HttpSession session;

	public List<UserDTO> selectRandomTwo() {
		
		String username = (String) session.getAttribute("username");
		String gender = (String) session.getAttribute("gender");
		
		log.info("selectRandomTwo...");
	    List<UserDTO> allUsers = userRepo.findAll(); // 모든 사용자를 가져옵니다.
	    log.info("allUsers:{}", allUsers);
	    
	    List<UserDTO> usersExceptLoggedIn = allUsers.stream()
	    		.filter(user -> !user.getUsername().equals(username))
	    		.filter(user -> !user.getGender().equals(gender))
	    		.collect(Collectors.toList());
	    int totalUsers = usersExceptLoggedIn.size();
	    
	    if (totalUsers < 2) {
	        log.warn("There are less than 2 users in the system.");
	        return null; // 2명 미만의 사용자가 있는 경우 null 반환
	    }

	    Random random = new Random();
	    int index1 = random.nextInt(totalUsers); // 첫 번째 랜덤한 인덱스 선택
	    int index2;
	    UserDTO user1 = usersExceptLoggedIn.get(index1); // 첫 번째 랜덤한 사용자
	    
	    // 두 번째 사용자를 선택할 때 첫 번째 사용자와 중복되지 않도록 처리
	    do {
	        index2 = random.nextInt(totalUsers); // 두 번째 랜덤한 인덱스 선택
	    } while (index2 == index1); // 두 번째 인덱스가 첫 번째 인덱스와 같은 경우 다시 선택

	    UserDTO user2 = usersExceptLoggedIn.get(index2); // 두 번째 랜덤한 사용자

	    return List.of(user1, user2); // 선택된 두 사용자를 리스트로 반환
	}

	public MeetingDTO registerMeeting(MeetingDTO dto) {
		return meetingRepo.save(dto);
	}



}
