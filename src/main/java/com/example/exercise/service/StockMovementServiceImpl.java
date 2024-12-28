package com.example.exercise.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exercise.models.entity.ItemEntity;
import com.example.exercise.models.entity.StockMovementEntity;
import com.example.exercise.models.web.MovementsOutput;
import com.example.exercise.models.web.StockMovementInput;
import com.example.exercise.service.access.AccessStockMovementRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockMovementServiceImpl {

	@Autowired
	private AccessStockMovementRepository accessStockMovementRepository;

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	public MovementsOutput getList() {
		log.info("START getList");
		try {
			return MovementsOutput.builder().movementsList(accessStockMovementRepository.findAll()).build();
		} catch (Exception e) {
			log.error("Error processing getList. Reason: {}", e.getMessage(), e);
			throw e;
		}
	}

	public StockMovementEntity getStock(Long itemId) {
		log.info("START getStock");
		try {
			List<StockMovementEntity> list = accessStockMovementRepository.findByItemId(itemId);
			return list.get(0);
		} catch (Exception e) {
			log.error("Error processing getStock {}. Reason: {}", itemId, e.getMessage(), e);
			throw e;
		}
	}
	
	@Transactional
	public void saveStockMovement(StockMovementInput input) {
		log.info("START saveStockMovement");
		try {
			ItemEntity item = itemServiceImpl.getItem(input.getItemId());
			accessStockMovementRepository.save(StockMovementEntity.builder().creationDate(new Date()).item(item)
					.quantity(input.getQuantity()).build());
			log.info("StockMovement created successfully.");
		} catch (Exception e) {
			log.error("Error processing saveStockMovement {}. Reason: ", e.getMessage(), e);
			throw e;
		}
	}


	@Transactional
	public void updateStockMovement(StockMovementInput input) {
		log.info("START saveStockMovement");
		try {
			ItemEntity item = itemServiceImpl.getItem(input.getItemId());
			StockMovementEntity stock = getStock(input.getItemId());
			if (stock.getQuantity() < input.getQuantity()) {
				throw new IllegalArgumentException("Insufficient stock for item. Requested quantity: "
						+ input.getQuantity() + ", available quantity: " + stock.getQuantity() + ".");
			}
			accessStockMovementRepository.save(StockMovementEntity.builder().creationDate(new Date()).item(item)
					.quantity(stock.getQuantity()-input.getQuantity()).build());
			log.info("StockMovement created successfully.");
		} catch (Exception e) {
			log.error("Error processing saveStockMovement {}. Reason: ", e.getMessage(), e);
			throw e;
		}
	}



	public void removeStockMovement(Long id) {
		accessStockMovementRepository.remove(id);		
	}

}
