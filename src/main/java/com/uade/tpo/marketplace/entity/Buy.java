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
import jakarta.persistence.OneToOne;
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
 //@NotNull
  @OneToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "purchasedDocument_id")
  @NotEmpty
  @OneToMany(mappedBy = "buy", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<BuyItem> itemsBuyed;
  @NotNull
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonBackReference
  private User user;
 
  public double getTotalPrice() {
    double totalPrice = 0;
    for (BuyItem itemBuyed : itemsBuyed) {
      totalPrice += itemBuyed.getSubTotal();
    }
    return totalPrice;
  }

  public void setItems(List<BuyItem> itemsBuyed) {
    itemsBuyed.forEach(itemBuyed -> itemBuyed.setBuy(this));
    this.itemsBuyed = itemsBuyed;
  }

  public BuyDTO toDTO() {
    return BuyDTO.builder()
        .id(this.id)
        .itemsBuyed(this.itemsBuyed)
        .buyDate(this.buyDate)
        .user(user)
        .totalPrice(this.getTotalPrice())
        .build();
  }

}