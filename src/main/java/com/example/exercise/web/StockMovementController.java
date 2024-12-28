package com.example.exercise.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise.models.web.MovementsOutput;
import com.example.exercise.models.web.StockMovementInput;
import com.example.exercise.service.StockMovementServiceImpl;

@RestController
@RequestMapping("/api/stockmovements")
public class StockMovementController {

	@Autowired
	private StockMovementServiceImpl stockMovementServiceImpl;

	@GetMapping
	public ResponseEntity<MovementsOutput> getAllStockMovement() {
		return ResponseEntity.ok(stockMovementServiceImpl.getList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createStockMovement(@RequestBody StockMovementInput stockMovement) {
		stockMovementServiceImpl.saveStockMovement(stockMovement);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {

		stockMovementServiceImpl.removeStockMovement(id);
		return ResponseEntity.noContent().build();
	}

}