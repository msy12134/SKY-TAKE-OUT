package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface Shoppingcartservice {
    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> getall();

    void delete();

    void removeone(ShoppingCartDTO shoppingCartDTO);
}
