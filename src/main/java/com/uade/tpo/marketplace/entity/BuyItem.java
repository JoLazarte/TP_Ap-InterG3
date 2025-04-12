package com.uade.tpo.marketplace.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private double finalPrice;
  
  @Column(nullable = false)
  private int totalQuantity;
  
  @ElementCollection
  @Column(nullable = false, columnDefinition = "LONGTEXT")
  private List<String> images;

  @ManyToOne
  @JoinColumn(nullable = false, name = "buy_id")
  @JsonBackReference
  private Buy buy;

  public double getSubTotal() {
    return this.finalPrice * this.totalQuantity;
  }

}