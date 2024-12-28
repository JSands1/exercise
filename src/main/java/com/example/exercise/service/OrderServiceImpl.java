package com.example.exercise.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.exercise.models.entity.ItemEntity;
import com.example.exercise.models.entity.OrderEntity;
import com.example.exercise.models.entity.UserEntity;
import com.example.exercise.models.web.OrderInput;
import com.example.exercise.models.web.StockMovementInput;
import com.example.exercise.service.access.AccessOrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl {

	@Autowired
	private AccessOrderRepository accessOrderRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	@Autowired
	private StockMovementServiceImpl stockMovementServiceImpl;

	@Autowired
	private JavaMailSender mailSender;

	public List<OrderEntity> getList() {
		return accessOrderRepository.findAll();
	}

	public OrderEntity getOrder(Long id) {
		log.info("START getOrder {}", id);
		return accessOrderRepository.findById(id).orElseThrow(() -> {
			String errorMessage = "Order with ID " + id + " not found.";
			log.error(errorMessage);
			return new EntityNotFoundException(errorMessage);
		});
	}

	public void saveOrder(OrderInput input) {
		log.info("START saveOrder");
		try {
			UserEntity user = userServiceImpl.getUser(input.getUserId());
			ItemEntity item = itemServiceImpl.getItem(input.getItemId());

			stockMovementServiceImpl.updateStockMovement(
					StockMovementInput.builder().itemId(item.getId()).quantity(input.getQuantity()).build());

			accessOrderRepository.save(OrderEntity.builder().creationDate(new Date()).user(user).item(item)
					.quantity(input.getQuantity()).isComplete(false).build());
		} catch (Exception e) {
			log.error("Error processing saveOrder. Reason: {}", e.getMessage(), e);
			throw e;
		}

	}

	public void updateOrder(Long id, OrderEntity orderDetails) {
		log.info("START updateOrder");
		try {

			Optional<OrderEntity> order = accessOrderRepository.findById(id);
			if (order.isPresent()) {
				OrderEntity existingOrder = order.get();
				if (orderDetails.getCreationDate() != null) {
					existingOrder.setCreationDate(orderDetails.getCreationDate());
				}
				if (orderDetails.getUser() != null) {
					existingOrder.setUser(orderDetails.getUser());
				}
				if (orderDetails.getItem() != null) {
					existingOrder.setItem(orderDetails.getItem());
				}
				if (orderDetails.getQuantity() > 0) {
					existingOrder.setQuantity(orderDetails.getQuantity());
				}
				if (orderDetails.getCreationDate() != null) {
					existingOrder.setCreationDate(orderDetails.getCreationDate());
				}
				if (orderDetails.isComplete()) {
					existingOrder.setComplete(true);
					//sendSimpleEmail(order.get().getUser().getEmail());

				}
				existingOrder.setComplete(orderDetails.isComplete());
				accessOrderRepository.save(existingOrder);
			} else {
				throw new EntityNotFoundException("Order with ID " + id + " not found.");
			}
		} catch (Exception e) {
			log.error("Error processing updateOrder {}. Reason: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	public void removeOrder(Long id) {
		log.info("START removeOrder");
		try {
			accessOrderRepository.remove(id);
		} catch (Exception e) {
			log.error("Error processing removeOrder {}. Reason: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	private void sendSimpleEmail(String to) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject("Order Executed Successfully");
			message.setText("Your order has been executed successfully.");
			message.setFrom("your_email@example.com");

			mailSender.send(message);
			log.info("Email sent successfully!");
		} catch (Exception e) {
			log.error("Error mailSender. Reason: {}", e.getMessage(), e);
			throw e;
		}
	}
}