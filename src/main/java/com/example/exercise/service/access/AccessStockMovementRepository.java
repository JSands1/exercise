package com.example.exercise.service.access;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.exercise.models.entity.StockMovementEntity;
import com.example.exercise.repository.StockMovementRepository;

@Component
public class AccessStockMovementRepository {
	@Autowired
	private StockMovementRepository stockMovementRepository;
	
	public List<StockMovementEntity> findAll(){
		return stockMovementRepository.findAll();
	}
	
	public List<StockMovementEntity> findByItemId(Long itemId){
		return stockMovementRepository.findByItemId(itemId);
	}
	
	public Optional<StockMovementEntity> findById(Long id){
		return stockMovementRepository.findById(id);
	}
	
	public void save(StockMovementEntity input) {
		stockMovementRepository.save(input);
	}
	
	public void remove(Long id) {
		stockMovementRepository.deleteById(id);
	}
	
	public boolean existsByItemId(Long itemId) {
		return stockMovementRepository.existsByItemId(itemId);
	}

}
