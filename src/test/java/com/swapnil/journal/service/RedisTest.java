package com.swapnil.journal.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Disabled
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testGetEmailFromRedis() {
        String email = redisTemplate.opsForValue().get("email");
        System.out.println("Email from Redis: " + email);
        redisTemplate.opsForValue().set("name","swapnil");

    }
}
