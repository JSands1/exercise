package com.example.exercise.service.access;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.exercise.models.entity.UserEntity;
import com.example.exercise.repository.UserRepository;

@Component
public class AccessUserRepository {
	@Autowired
	private UserRepository UserRepository;
	
	public List<UserEntity> findAll(){
		return UserRepository.findAll();
	}
	
	public Optional<UserEntity> findById(Long id){
		return UserRepository.findById(id);
	}
	
	public void save(UserEntity input) {
		UserRepository.save(input);
	}
	
	public void remove(Long id) {
		UserRepository.deleteById(id);
	}

}
