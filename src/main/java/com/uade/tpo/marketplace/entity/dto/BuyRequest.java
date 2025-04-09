package com.uade.tpo.marketplace.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.User;
import lombok.Data;

@Data
public class BuyRequest {
    private Long id;
    private LocalDateTime buyDate;
    private List<CartItem> items;
    private User user;
}
