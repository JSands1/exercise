package com.example.exercise.service.access;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.exercise.models.entity.OrderEntity;
import com.example.exercise.repository.OrderRepository;



@Component
public class AccessOrderRepository {
	@Autowired
	private OrderRepository orderRepository;
	
	public List<OrderEntity> findAll(){
		return orderRepository.findAll();
	}
	
	public Optional<OrderEntity> findById(Long id){
		return orderRepository.findById(id);
	}
	
	public void save(OrderEntity input) {
		orderRepository.save(input);
	}
	
	public void remove(Long id) {
		orderRepository.deleteById(id);
	}

}
