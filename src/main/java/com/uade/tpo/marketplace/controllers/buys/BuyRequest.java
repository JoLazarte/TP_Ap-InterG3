package com.uade.tpo.marketplace.controllers.buys;

import java.time.LocalDateTime;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.User;
import lombok.Data;

@Data
public class BuyRequest {
    //private Long id;
    private LocalDateTime buyDate;
    private Cart cart;
    private User user;
}
