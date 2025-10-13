package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("AdminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关的接口呢")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String STATUS_KEY = "SHOP_STATUS";
    /**
     * 设置店铺的营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("这是店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置营业状态为{}",status == 1?"营业中":"打烊中");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(STATUS_KEY, status);
        return Result.success();
    }
    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer) valueOperations.get(STATUS_KEY);
        log.info("获取营业状态为{}",status == 1?"营业中":"打烊中");

        return Result.success(status);
    }


}
