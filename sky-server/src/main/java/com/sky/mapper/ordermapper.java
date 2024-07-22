package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ordermapper {



    void insert(Orders orders);



    @Select("select * from sky_take_out.orders where status =#{status} and order_time < #{orderTime}")
    List<Orders> getbystatusandordertime(Integer status, LocalDateTime orderTime);

    @Update("update sky_take_out.orders set status=#{status}, cancel_reason=#{cancelReason},cancel_time=#{cancelTime} " +
            "where id=#{id}")
    void update(Orders orders);
}
