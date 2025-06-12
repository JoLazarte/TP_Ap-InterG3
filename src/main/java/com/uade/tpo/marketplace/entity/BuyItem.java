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
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
  @Column(name = "product_id", nullable = false)
  private Long productId;
  @Enumerated(EnumType.STRING)
  @Column(name = "product_type", nullable = false)
  private ProductType productType;
  @NotNull
  @Column(nullable = false)
  private Integer quantity;
  @NotNull
  @Column(name = "unit_price", nullable = false)
  private Double unitPrice;
  @Column(columnDefinition = "LONGTEXT")
  private String images;
  @NotNull
  @ManyToOne
  @JoinColumn(name = "buy_id", nullable = false)
  @JsonBackReference
  private Buy buy;

  public double getSubTotal() {
    return this.unitPrice * this.quantity;
  }

  public enum ProductType {
    BOOK,
    MUSIC_ALBUM
  }
}