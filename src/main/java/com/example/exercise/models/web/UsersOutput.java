package com.example.exercise.models.web;

import java.util.List;

import com.example.exercise.models.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersOutput {
	private List<UserEntity> listUsers;
}
