package com.example.exercise.web;

import java.util.List;

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

import com.example.exercise.models.entity.OrderEntity;
import com.example.exercise.models.web.OrderInput;
import com.example.exercise.service.OrderServiceImpl;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

   @Autowired
   private OrderServiceImpl orderServiceImpl;

   @GetMapping
   public ResponseEntity<List<OrderEntity>> getAllOrders() {
       return ResponseEntity.ok(orderServiceImpl.getList());
   }

   @GetMapping("/{id}")
   public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
   	return ResponseEntity.ok(orderServiceImpl.getOrder(id));
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public void createOrder(@RequestBody OrderInput order) {
	   orderServiceImpl.saveOrder(order);
   }

   @PutMapping("/{id}")
   @ResponseStatus(HttpStatus.ACCEPTED)
   public void updateOrder(@PathVariable Long id, @RequestBody OrderEntity orderDetails) {
	   orderServiceImpl.updateOrder(id, orderDetails);
   }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.ACCEPTED)
   public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
  
	   orderServiceImpl.removeOrder(id);
   	return ResponseEntity.noContent().build();
   }
}