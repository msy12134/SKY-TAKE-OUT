package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface dishservice {

    public void savewithFlavor(DishDTO dishDTO);

    PageResult pagequery(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);

    DishVO finddishwithflavor(Long id);

    void update_dish_and_flavor(DishDTO dishDTO);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */

}
