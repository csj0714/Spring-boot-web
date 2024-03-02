package com.example.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;

	public UserDTO insertOK(UserDTO vo) {
		return userRepo.save(vo);
	}

	public UserDTO selectOne(UserDTO vo) {
		return userRepo.findByNum(vo.getNum());
	}

	public UserDTO updateOK(UserDTO vo) {
		return userRepo.save(vo);
	}

	public int deleteOK(UserDTO vo) {
		return userRepo.deleteByNum(vo.getNum());
	}
	public UserDTO loginOK(UserDTO vo) {
		return userRepo.findByUsernameAndPw(vo.getUsername(),vo.getPw());
	}

	

}
