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

import com.example.exercise.models.entity.ItemEntity;
import com.example.exercise.models.web.ItemOutput;
import com.example.exercise.service.ItemServiceImpl;

@RestController
@RequestMapping("/api/items")
public class ItemController {
	
	@Autowired
    private ItemServiceImpl itemServiceImpl;


    @GetMapping
    public ResponseEntity<ItemOutput> getAllItems() {
        return ResponseEntity.ok(itemServiceImpl.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long id) {
    	return ResponseEntity.ok(itemServiceImpl.getItem(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody ItemEntity item) {
        itemServiceImpl.saveItem(item);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateItem(@PathVariable Long id, @RequestBody ItemEntity itemDetails) {
    	itemServiceImpl.updateItem(id, itemDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
   
    	itemServiceImpl.removeItem(id);
    	return ResponseEntity.noContent().build();
    }
}