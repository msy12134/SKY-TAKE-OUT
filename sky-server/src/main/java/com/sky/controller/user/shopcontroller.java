package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController(value = "shopcontroller_user")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
public class shopcontroller {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/status")
    @ApiOperation("用户查询店铺营业状态")
    public Result<Integer> getshopstatus(){
        log.info("获取店铺营业状态");
        Integer status = (Integer) redisTemplate.opsForValue().get("shopstatus");
        return Result.success(status);
    }
}
