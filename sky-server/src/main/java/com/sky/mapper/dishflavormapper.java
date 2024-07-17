package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface dishflavormapper {
    void insertbatch(List<DishFlavor> dishFlavors);


    @Delete("delete from sky_take_out.dish_flavor where dish_id=#{id}")
    void deletebyid(Long id);


    @Select("select * from sky_take_out.dish_flavor where dish_id=#{id}")
    List<DishFlavor> getbyid(Long id);


    @Insert("insert into sky_take_out.dish_flavor (dish_id, name, value) VALUES " +
            "(#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);
}
