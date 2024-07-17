package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface dishservice {

    public void savewithFlavor(DishDTO dishDTO);

    PageResult pagequery(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);
}
