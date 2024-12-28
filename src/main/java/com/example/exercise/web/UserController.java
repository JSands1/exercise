package com.example.exercise.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise.models.entity.UserEntity;
import com.example.exercise.models.web.UsersOutput;
import com.example.exercise.service.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
	
    @GetMapping
    public ResponseEntity<UsersOutput> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
    	return ResponseEntity.ok(userServiceImpl.getUser(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserEntity user) {
    	userServiceImpl.saveUser(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
    	userServiceImpl.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
   
    	userServiceImpl.removeUser(id);
    	return ResponseEntity.noContent().build();
    }
}