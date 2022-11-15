package com.example.capstone.service.contraindicate;

import com.example.capstone.entity.contraindicate.Contraindicate;
import com.example.capstone.exception.NotFoundContraindicate;
import com.example.capstone.repository.contraindicate.ContraindicateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ContraindicateService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ContraindicateRepository contraindicateRepository;

    @Transactional(readOnly = true)
    public String findContraindicate(String pillA, String pillB) {
        Contraindicate contraindicate = contraindicateRepository.findByPillAAndPillB(pillA, pillB).orElseThrow(
                NotFoundContraindicate::new);

        setRedis(pillA, contraindicate.getSymptom());
        return getRedis(pillA);
    }

    public void setRedis(String key, String symptom) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, symptom);
    }

    public String getRedis(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String symptom = values.get(key);
        return symptom;
    }
}
