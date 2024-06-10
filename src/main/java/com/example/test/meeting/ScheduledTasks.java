/*
 * package com.example.test.meeting;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Component; import
 * org.springframework.stereotype.Service; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.example.test.user.UserDTO;
 * 
 * import jakarta.servlet.http.HttpSession; import lombok.extern.slf4j.Slf4j;
 * 
 * @Component
 * 
 * @Slf4j
 * 
 * @Service public class ScheduledTasks {
 * 
 * @Autowired private MeetingService meetingService;
 * 
 * @Autowired private RestTemplate restTemplate;
 * 
 * @Scheduled(fixedDelay = 5000) public void resetSelectedUsers() throws
 * InterruptedException { String url = "http://localhost:8806/getSessionInfo";
 * String sessionInfo = restTemplate.getForObject(url, String.class);
 * System.out.println("Session Info: " + sessionInfo); } }
 */

