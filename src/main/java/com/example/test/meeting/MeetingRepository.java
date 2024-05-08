package com.example.test.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.user.UserDTO;

@Repository
public interface MeetingRepository extends JpaRepository<UserDTO, Object> {

	public UserDTO findByNum(int num);

}
