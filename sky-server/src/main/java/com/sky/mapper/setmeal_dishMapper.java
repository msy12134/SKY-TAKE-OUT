package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface setmeal_dishMapper {

    @Select("select * from sky_take_out.setmeal_dish where dish_id=id")
    SetmealDish getbydishid(Long id);
}
