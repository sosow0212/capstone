package com.example.capstone.service.contraindicate;

import com.example.capstone.entity.contraindicate.Contraindicate;
import com.example.capstone.exception.ContraindicateNotFoundException;
import com.example.capstone.repository.contraindicate.ContraindicateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ContraindicateService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ContraindicateRepository contraindicateRepository;

    @Transactional
    public Object findContraindicate(String pillA, String pillB) {
        if (getRedis(pillA, pillB) == null) {
            // 레디스에서 조회 했는데 없는 경우
            // 1. 디비 조회
            Contraindicate contraindicate = contraindicateRepository.findByPillAAndPillB(pillA, pillB)
                    .orElseThrow(ContraindicateNotFoundException::new);

            // 2. viewCount 증가 (if >= 3 추가)
            contraindicate.addViewCount();
            if (contraindicate.getViewCount() >= 3) {
                setRedis(pillA, pillB, contraindicate.getSymptom());
            }
            return contraindicate.getSymptom();
        }
        // 레디스에서 조회 했는데 있는 경우
        // 출력
        return getRedis(pillA, pillB);
    }

    public void setRedis(String key, String pillVal, String symptom) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, pillVal, symptom);
    }

    public Object getRedis(String key, String pillVal) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Object symptom = hashOperations.get(key, pillVal);
        return symptom;
    }
}
