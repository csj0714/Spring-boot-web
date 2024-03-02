package com.example.test.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Object> {

	public UserDTO findByNum(int num);

	public int deleteByNum(int num);

	public UserDTO findByUsernameAndPw(String username, String pw);

}
