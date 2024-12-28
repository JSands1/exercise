package com.example.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.exercise.models.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}