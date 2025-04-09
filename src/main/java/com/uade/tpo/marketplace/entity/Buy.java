package com.uade.tpo.marketplace.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
 
  @OneToMany(mappedBy = "buy", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<CartItem> items;
  @OneToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "purchasedDocument_id")
  @JsonBackReference
  private PurchaseDocument purchaseDocument;
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonBackReference
  private User user;

  public double getTotalPrice() {
    double totalPrice = 0;
    for (CartItem item : items) {
      totalPrice += item.calculateTotal();
    }
    return totalPrice;

  }

  

}