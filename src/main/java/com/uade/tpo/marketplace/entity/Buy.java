package com.uade.tpo.marketplace.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false)
  private LocalDateTime buyDate;
 
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  private Cart cart;
  @OneToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "purchasedDocument_id")
  private PurchaseDocument purchaseDocument;
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  

}