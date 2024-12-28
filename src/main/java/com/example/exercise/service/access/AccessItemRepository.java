package com.example.exercise.service.access;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.exercise.models.entity.ItemEntity;
import com.example.exercise.repository.ItemRepository;

@Component
public class AccessItemRepository {
	@Autowired
	private ItemRepository itemRepository;
	
	public List<ItemEntity> findAll(){
		return itemRepository.findAll();
	}
	
	public Optional<ItemEntity> findById(Long id){
		return itemRepository.findById(id);
	}
	
	public void save(ItemEntity input) {
		itemRepository.save(input);
	}
	
	public void remove(Long id) {
		itemRepository.deleteById(id);
	}

}
