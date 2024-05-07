package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class userService {

	@Autowired(required=true)
	private userRepository userRepository;
	
    public List<userEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public userEntity findUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public userEntity saveUser(userEntity user) {
        return userRepository.save(user);
    }
	
	
}
