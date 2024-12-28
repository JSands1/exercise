package com.example.exercise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exercise.models.entity.ItemEntity;
import com.example.exercise.models.web.ItemOutput;
import com.example.exercise.service.access.AccessItemRepository;
import com.example.exercise.service.access.AccessStockMovementRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl {

	@Autowired
	private AccessItemRepository accessItemRepository;
	
	@Autowired
	private AccessStockMovementRepository accessStockMovementRepository;

	public ItemOutput getList() {
		log.info("START getList");
		try {
			List<ItemEntity> list = accessItemRepository.findAll();

			return ItemOutput.builder().itemsList(list).build();
		} catch (Exception e) {
			log.error("Error processing getList. Reason: {}", e.getMessage(), e);
			throw e;
		}
	}

	public ItemEntity getItem(Long id) {
		log.info("START getItem {}", id);
		Optional<ItemEntity> item = accessItemRepository.findById(id);
		if (item.isPresent()) {
			return item.get();
		}
		log.error("Item with ID " + id + " not found.");
		throw new EntityNotFoundException("Item with ID " + id + " not found.");
	}

	public void saveItem(ItemEntity input) {
		accessItemRepository.save(input);
	}

	public void updateItem(Long id, ItemEntity itemDetails) {
		log.info("START updateItem {}", id);
		Optional<ItemEntity> item = accessItemRepository.findById(id);
		if (item.isPresent()) {
			item.get().setName(itemDetails.getName());
			accessItemRepository.save(item.get());
		} else {
			log.error("Item with ID " + id + " not found.");
			throw new EntityNotFoundException("Item with ID " + id + " not found.");
		}
	}

	public void removeItem(Long id) {
		log.info("START removeItem {}", id);
		try {
			if (accessStockMovementRepository.existsByItemId(id)) {
			    throw new RuntimeException("Cannot delete item. It is referenced in stock movements.");
			}
			accessItemRepository.remove(id);
		} catch (Exception e) {
			log.error("Error processing removeItem. Reason: {}", e.getMessage(), e);
			throw e;
		}
		
	}
}
