package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface usermapper {

    @Select("select * from sky_take_out.user where openid=#{code};")
    User get(String code);

    void insert(User newuser);
}
