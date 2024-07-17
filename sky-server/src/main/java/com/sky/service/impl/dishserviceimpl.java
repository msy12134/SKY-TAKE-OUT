package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.mapper.dishflavormapper;
import com.sky.service.dishservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class dishserviceimpl implements dishservice {


    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private dishflavormapper dishflavormapper;
    @Override
    @Transactional
    public void savewithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if(dishFlavors != null && !dishFlavors.isEmpty()) {
            for(DishFlavor dishFlavor : dishFlavors) {
                dishFlavor.setDishId(dishId);
            }
            dishflavormapper.insertbatch(dishFlavors);

        }
    }
}
