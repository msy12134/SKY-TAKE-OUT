package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.dishflavormapper;
import com.sky.mapper.setmeal_dishMapper;
import com.sky.result.PageResult;
import com.sky.service.dishservice;
import com.sky.vo.DishVO;
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
    private setmeal_dishMapper setmealdishMapper;
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

    @Override
    public PageResult pagequery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pagequery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //当前菜品是不是在买
        for(Long id : ids) {
            Dish dish=dishMapper.getbyid(id);
            if(dish!=null && dish.getStatus()==StatusConstant.DISABLE){
                throw new DeletionNotAllowedException("菜品正在启用中");
            }
        }
        for(Long id : ids) {
            SetmealDish setmealDish=setmealdishMapper.getbydishid(id);
            if (setmealDish!=null){
                throw new DeletionNotAllowedException("菜品被套餐包含");
            }
        }

        //是否被套餐关联
        for (Long id : ids){
            dishMapper.deletebyid(id);
            dishflavormapper.deletebyid(id);
        }

        //删除菜品数据，同时删除菜品关联的口味数据


    }

    @Override
    public DishVO finddishwithflavor(Long id) {
        Dish dish= dishMapper.getbyid(id);
        List<DishFlavor> dishFlavors=dishflavormapper.getbyid(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void update_dish_and_flavor(DishDTO dishDTO) {
        Dish dish =new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.updatedish(dish);
        dishflavormapper.deletebyid(dishDTO.getId());
        List<DishFlavor> dishFlavors=dishDTO.getFlavors();
        for(DishFlavor dishFlavor : dishFlavors){
            dishFlavor.setDishId(dish.getId());
            dishflavormapper.insert(dishFlavor);
        }
    }
}
