package com.example.exercise.models.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class OrderEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date creationDate;
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(nullable = false)
    private ItemEntity item;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private boolean isComplete = false;
    
    
}