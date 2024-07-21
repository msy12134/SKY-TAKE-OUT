package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.shoppingcartMapper;
import com.sky.service.Shoppingcartservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Shoppingcartservicelmpl implements Shoppingcartservice {

    @Autowired
    private shoppingcartMapper shoppingcartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

//        shoppingCart=shoppingcartMapper.select(shoppingCart);
        if(shoppingcartMapper.select(shoppingCart)!=null){
            shoppingCart=shoppingcartMapper.select(shoppingCart);
            log.info("现存购物车中已经找到对应表项，现在只要加一即可{}",shoppingCart);
            shoppingCart.setNumber(shoppingCart.getNumber()+1);
            shoppingcartMapper.update(shoppingCart);
        }
        else{
            shoppingCart.setUserId(BaseContext.getCurrentId());
            BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
            if(shoppingCart.getDishId()!=null){
               Dish dish= dishMapper.getbyid(shoppingCart.getDishId());
               shoppingCart.setName(dish.getName());
               shoppingCart.setNumber(1);
               shoppingCart.setAmount(dish.getPrice());
               shoppingCart.setImage(dish.getImage());
               shoppingCart.setCreateTime(LocalDateTime.now());
            } else if (shoppingCart.getSetmealId()!=null) {
                Setmeal setmeal=setmealMapper.getbyid(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setCreateTime(LocalDateTime.now());
            }
            shoppingcartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> getall() {
        Long userid=BaseContext.getCurrentId();
        List<ShoppingCart> list=shoppingcartMapper.getall(userid);
        return list;
    }

    @Override
    public void delete() {
        Long userid=BaseContext.getCurrentId();
        shoppingcartMapper.delete(userid);
    }

    @Override
    public void removeone(ShoppingCartDTO shoppingCartDTO) {
        Long userid=BaseContext.getCurrentId();
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(userid);
        shoppingcartMapper.removeone(shoppingCart);
    }
}
