package com.example.test.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDTO, Object> {

	public UserDTO findByNum(int num);

	public UserDTO findByUsernameAndPw(String username, String pw);

	public UserDTO findByUsername(String username);

	public int deleteByUsername(String username);

	public int deleteByNum(int num);

}
