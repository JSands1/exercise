package com.example.exercise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.exercise.models.entity.StockMovementEntity;

public interface StockMovementRepository extends JpaRepository<StockMovementEntity, Long> {
	
	 @Query("SELECT DISTINCT  s FROM StockMovementEntity s WHERE s.item.id=:itemId")
	 List<StockMovementEntity> findByItemId(Long itemId);
	 
	 @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM StockMovementEntity s WHERE s.item.id = :itemId")
	 boolean existsByItemId(Long itemId);
}