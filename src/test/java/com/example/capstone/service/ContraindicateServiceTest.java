package com.example.capstone.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.capstone.entity.contraindicate.Contraindicate;
import com.example.capstone.repository.contraindicate.ContraindicateRepository;
import com.example.capstone.service.contraindicate.ContraindicateService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class ContraindicateServiceTest {

    @Autowired
    ContraindicateService contraindicateService;

    @Autowired
    ContraindicateRepository contraindicateRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("Redis 테스트")
    public void redisTest() {
        // given
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        // when
        hashOperations.put("key", "hashKey1", "val1");
        hashOperations.put("key", "hashKey2", "val2");

        // then
        Object value1 = hashOperations.get("key", "hashKey1");
        Object value2 = hashOperations.get("key", "hashKey2");

        assertThat(hashOperations.get("key", "hashKey1")).isEqualTo(value1);
        assertThat(hashOperations.get("key", "hashKey2")).isEqualTo(value2);

        hashOperations.delete("key", "hashKey1");
        hashOperations.delete("key", "hashKey2");
    }

    @Test
    @DisplayName("findContraindicate 테스트 (레디스에 있는 경우)")
    public void findContraindicateOnRedisTest() {
        // given
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String pillA = "testPillA";
        String pillB = "testPillB";
        String symptom = "testSymptom";
        hashOperations.put(pillA, pillB, symptom);

        // when
        Object result = contraindicateService.findContraindicate(pillA, pillB);

        // then
        assertThat(result).isEqualTo(symptom);
        hashOperations.delete(pillA, pillB);
    }
}