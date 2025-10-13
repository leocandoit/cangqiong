package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean //这个是方法的注解，方法返回的对象交给容器管理
    //像@Component注解，这是让Spring自己去new一个对象出来
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("redisTemplate创建呢～");
        RedisTemplate redisTemplate= new RedisTemplate();
        // 设置连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
