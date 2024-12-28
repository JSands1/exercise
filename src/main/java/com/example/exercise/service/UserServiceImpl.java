package com.example.exercise.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exercise.models.entity.UserEntity;
import com.example.exercise.models.web.UsersOutput;
import com.example.exercise.service.access.AccessUserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl {

	@Autowired
	private AccessUserRepository accessUserRepository;

	public UsersOutput getList() {
		log.info("START getList");
		try {
			return UsersOutput.builder().listUsers(accessUserRepository.findAll()).build();
		} catch (Exception e) {
			log.error("Error processing updateOrder. Reason: {}", e.getMessage(), e);
			throw e;
		}
	}

	public UserEntity getUser(Long id) {
		log.info("START getUser");
		return accessUserRepository.findById(id).orElseThrow(() -> {
			String errorMessage = "User with ID " + id + " not found.";
			log.error(errorMessage);
			return new EntityNotFoundException(errorMessage);
		});
	}

	public void saveUser(UserEntity input) {
		log.info("START saveUser");
		try {
			accessUserRepository.save(input);
		} catch (Exception e) {
			log.error("Error processing updateOrder. Reason: {}", e.getMessage(), e);
			throw e;
		}
	}

	public void updateUser(Long id, UserEntity userDetails) {
		log.info("START updateUser");
		try {
			Optional<UserEntity> user = accessUserRepository.findById(id);
			if (user.isPresent()) {
				if (userDetails.getName() != null) {
					user.get().setName(userDetails.getName());
				}
				if (userDetails.getEmail() != null) {
					user.get().setEmail(userDetails.getEmail());
				}
				accessUserRepository.save(user.get());
			} else {
				throw new EntityNotFoundException("User with ID " + id + " not found.");
			}
		} catch (Exception e) {
			log.error("Error processing updateUser {}. Reason: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	public void removeUser(Long id) {
		try {
			accessUserRepository.remove(id);
		} catch (Exception e) {
			log.error("Error processing removeUser {}. Reason: {}", id, e.getMessage(), e);
			throw e;
		}
	}
}
