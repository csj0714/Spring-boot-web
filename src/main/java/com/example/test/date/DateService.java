package com.example.test.date;

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
public class DateService {
	
	@Autowired
	private DateRepository DateRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HttpSession session;
	
    private List<UserDTO> selectedUsers;
    private long lastSelectedTime;
    private final Lock lock = new ReentrantLock();

    public List<UserDTO> selectRandomTwo(String username, String gender) {
        long currentTime = System.currentTimeMillis();
        // 60초 이내에 호출되면 이전에 선택된 사용자를 반환
        if (selectedUsers != null && (currentTime - lastSelectedTime) < 24 * 3600 * 1000) {
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


	public DateDTO registerDate(DateDTO dto) {
		log.info("디티오:{}",dto.toString());
		return DateRepo.save(dto);
	}

	public List<DateDTO> selectAll() {
		return DateRepo.findAll();
	}


	public List<DateDTO> selectOne(int num) {
		return DateRepo.findByApplicantNum(num);
	}


	public List<DateDTO> selectReceiver(int receiverNum) {
		return DateRepo.findByApplicantNum(receiverNum);
	}


	public DateDTO selectByReceiverAndApplicant(int receiverNum, int applicantNum) {
		return DateRepo.findByReceiverNumAndApplicantNum(receiverNum, applicantNum);
	}

	public int deleteByReceiverAndApplicant(int receiverNum, int applicantNum) {
		return DateRepo.deleteByReceiverNumAndApplicantNum(receiverNum, applicantNum);
	}
	@Transactional
	public int deleteByNum(int num) {
		return DateRepo.deleteByNum(num);
	}


}

