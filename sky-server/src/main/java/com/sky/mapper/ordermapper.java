package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ordermapper {



    void insert(Orders orders);
}
