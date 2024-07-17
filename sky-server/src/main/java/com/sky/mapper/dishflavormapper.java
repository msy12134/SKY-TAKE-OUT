package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface dishflavormapper {
    void insertbatch(List<DishFlavor> dishFlavors);


    @Delete("delete from sky_take_out.dish_flavor where dish_id=#{id}")
    void deletebyid(Long id);
}
