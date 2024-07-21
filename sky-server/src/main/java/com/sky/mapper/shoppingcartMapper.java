package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface shoppingcartMapper {

    @Update("update sky_take_out.shopping_cart set number=#{number} where id=#{id}")
    void update(ShoppingCart shoppingCart);

    ShoppingCart select(ShoppingCart shoppingCart);


    @Insert("insert into sky_take_out.shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) VALUES " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);


    @Select("select * from sky_take_out.shopping_cart where user_id=#{userId}")
    List<ShoppingCart> getall(Long userId);


    @Delete("delete from sky_take_out.shopping_cart where user_id=#{userid}")
    void delete(Long userid);

    void removeone(ShoppingCart shoppingCart);
}
