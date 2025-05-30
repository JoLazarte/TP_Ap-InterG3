package com.uade.tpo.marketplace.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.controllers.buys.BuyDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
  @Column(nullable = false)
  private LocalDateTime buyDate;
 
  //@NotEmpty
  @OneToMany(mappedBy = "buy", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<BuyItem> items;
  @NotNull
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonBackReference
  private User user;
 
  public double getTotalPrice() {
    double totalPrice = 0;
    for (BuyItem item : items) {
      totalPrice += item.getSubTotal();
    }
    return totalPrice;
  }

  public void setItems(List<BuyItem> itemsB) {
    itemsB.forEach(itemB -> itemB.setBuy(this));
    this.items = itemsB;
  }

  public BuyDTO toDTO() {
    return BuyDTO.builder()
        .id(this.id)
        .items(this.items)
        .buyDate(this.buyDate)
        .user(user)
        .totalPrice(this.getTotalPrice())
        .build();
  }

}