package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotEmpty;
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
  @NotNull
  @Column(nullable = false)
  private String title;
  @NotNull
  @Column(nullable = false)
  private String description;
  @NotNull
  @Column(nullable = false)
  private double finalPrice;
  @NotNull
  @Column(nullable = false)
  private int totalQuantity;
  //@NotEmpty
  @Column(nullable = false, columnDefinition = "LONGTEXT")
  private String images;
  @NotNull
  @ManyToOne
  @JoinColumn(nullable = false, name = "buy_id")
  @JsonBackReference
  private Buy buy;

  public double getSubTotal() {
    return this.finalPrice * this.totalQuantity;
  }

}