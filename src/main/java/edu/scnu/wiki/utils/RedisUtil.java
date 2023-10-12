package edu.scnu.wiki.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author long
 * @version 1.0
 * @ClassName RedisUtil
 * @description: TODO
 * @date 2023/10/11 10:06
 */
@Slf4j
@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;


    public boolean validateRepeat(String key, long second){
        if (redisTemplate.hasKey(key)){
            log.info("key已经存在:{}", key);
            return false;
        }else {
            log.info("key不存在,放入:{},过期{}秒", key, second);
            redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
            return true;
        }

    }



}
