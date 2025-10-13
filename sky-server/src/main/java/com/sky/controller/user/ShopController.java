package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("UserShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关的接口呢")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String STATUS_KEY = "SHOP_STATUS";
    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer status = (Integer) valueOperations.get(STATUS_KEY);
        log.info("获取营业状态为{}",status == 1?"营业中":"打烊中");

        return Result.success(status);
    }


}
