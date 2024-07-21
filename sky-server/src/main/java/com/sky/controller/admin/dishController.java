package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.dishservice;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class dishController {
    @Autowired
    private dishservice  dishservice;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品{}",dishDTO);
        dishservice.savewithFlavor(dishDTO);

        String key="dish_"+dishDTO.getCategoryId();
        redisTemplate.delete(key);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pagequery(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询{}",dishPageQueryDTO);
        PageResult pageResult=dishservice.pagequery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping()
    @ApiOperation("删除菜品")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品{}",ids);
        dishservice.delete(ids);
        Set keys=redisTemplate.keys("dish_");
        redisTemplate.delete(keys);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询菜品已经相应的口味数据")
    public Result<DishVO> finddishwithflavor(@PathVariable Long id ){
        log.info("菜品id是{}",id);
        DishVO dishVO=dishservice.finddishwithflavor(id);
        return Result.success(dishVO);
    }
    @PutMapping
    @ApiOperation("更新菜品及其口味数据")
    public Result update_dish_and_flavor(@RequestBody DishDTO dishDTO){
        log.info("更新数据为{}",dishDTO);
        dishservice.update_dish_and_flavor(dishDTO);
        Set keys=redisTemplate.keys("dish_");
        redisTemplate.delete(keys);

        return Result.success();
    }
}
