package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.Shoppingcartservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车接口")
public class shoppingcartController {
    @Autowired
    private Shoppingcartservice shoppingcartservice;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingcartservice.add(shoppingCartDTO);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> getAll() {
        log.info("查看购物车中的所有东西");
        List<ShoppingCart> list=shoppingcartservice.getall();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result delete(){
        log.info("清空购物车");
        shoppingcartservice.delete();
        return Result.success();
    }


    @PostMapping("/sub")
    @ApiOperation("删除购物车中的一个商品")
    public Result removeone(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("删除购物车中的一个商品：{}", shoppingCartDTO);
        shoppingcartservice.removeone(shoppingCartDTO);
        return Result.success();
    }
}
