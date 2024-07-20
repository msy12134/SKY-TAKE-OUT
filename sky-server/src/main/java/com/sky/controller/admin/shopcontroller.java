package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class shopcontroller {
    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setshopstatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为：{}",status);
        redisTemplate.opsForValue().set("shopstatus",status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("商家查询店铺营业状态")
    public Result<Integer> getshopstatus(){
        log.info("获取店铺营业状态");
        Integer status = (Integer) redisTemplate.opsForValue().get("shopstatus");
        return Result.success(status);
    }
}
