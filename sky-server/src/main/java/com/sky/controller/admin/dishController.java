package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.dishservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class dishController {
    @Autowired
    private dishservice  dishservice;


    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品{}",dishDTO);
        dishservice.savewithFlavor(dishDTO);
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
        return Result.success();
    }
}
