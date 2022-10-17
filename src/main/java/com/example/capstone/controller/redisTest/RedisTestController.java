package com.example.capstone.controller.redisTest;

import com.example.capstone.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RedisTestController {

    private final RedisTemplate<String, String> redisTemplate;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/data")
    public Response setRedisData(@RequestBody(required = true) Map<String, String> map) {
        redisTemplate.opsForValue().set(map.get("key"), map.get("value"));
        return Response.success("성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/data")
    public Response getRedisData(@RequestParam(required = true) String key) {
        return Response.success(redisTemplate.opsForValue().get(key));
    }
}
