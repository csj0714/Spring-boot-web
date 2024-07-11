package com.example.test.meeting;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.user.UserDTO;
import com.example.test.user.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetingService {
	
	@Autowired
	private MeetingRepository meetingRepo;
	
	@Autowired
	private MeetingApplicationRepository meetingApplRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HttpSession session;
	
    private List<UserDTO> selectedUsers;
    private List<MeetingDTO> selectedMeetings;
    
    private long lastSelectedTime;
    private final Lock lock = new ReentrantLock();

    public List<UserDTO> selectRandomTwo(String username, String gender) {
        long currentTime = System.currentTimeMillis();
        // 60초 이내에 호출되면 이전에 선택된 사용자를 반환
        if (selectedUsers != null && (currentTime - lastSelectedTime) < 5000) {
            return selectedUsers;
        }

        lock.lock(); // 락 획득
        try {
            log.info("selectRandomTwo...");

            if (username == null || gender == null) {
                log.warn("Username or gender not found.");
                return null;
            }

            List<UserDTO> allUsers = userRepo.findAll();
            log.info("allUsers: {}", allUsers);

            List<UserDTO> usersExceptLoggedIn = allUsers.stream()
                    .filter(user -> !user.getUsername().equals(username))
                    .filter(user -> !user.getGender().equals(gender))
                    .collect(Collectors.toList());
            int totalUsers = usersExceptLoggedIn.size();

            if (totalUsers < 2) {
                log.warn("There are less than 2 users in the system.");
                return null;
            }

            Random random = new Random();
            int index1 = random.nextInt(totalUsers);
            int index2;
            UserDTO user1 = usersExceptLoggedIn.get(index1);

            do {
                index2 = random.nextInt(totalUsers);
            } while (index2 == index1);

            UserDTO user2 = usersExceptLoggedIn.get(index2);

            selectedUsers = List.of(user1, user2);
            lastSelectedTime = currentTime;

            return selectedUsers;
        } finally {
            lock.unlock(); // 락 해제
        }
    }

    @Transactional
	public MeetingRegister registerMeeting(MeetingRegister register) {
		return meetingApplRepo.save(register);
	}

	public List<MeetingDTO> selectAll() {
		return meetingRepo.findAll();
	}





	public List<MeetingDTO> ramdomMeetingTwo(UserDTO currentUser) {
		List<MeetingDTO> allMeetings = meetingRepo.findAll();

        // 현재 사용자가 주최자이거나 이미 신청한 미팅을 제외
        List<MeetingDTO> filteredMeetings = allMeetings.stream()
            .filter(meeting -> meeting.getOrganizerNum() != currentUser.getNum())
            .collect(Collectors.toList());

        // 두 개의 랜덤 미팅 선택
        Random random = new Random();
        return random.ints(0, filteredMeetings.size())
            .distinct()
            .limit(2)
            .mapToObj(filteredMeetings::get)
            .collect(Collectors.toList());
    }


	public MeetingDTO insertOK(MeetingDTO vo) {
		return meetingRepo.save(vo);
	}
}

