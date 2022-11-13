package com.example.capstone.service.contraindicate;

import com.example.capstone.dto.contraindicate.RequestContraindicateDto;
import com.example.capstone.entity.contraindicate.Contraindicate;
import com.example.capstone.exception.NotFoundContraindicate;
import com.example.capstone.repository.contraindicate.ContraindicateRepository;
import com.example.capstone.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ContraindicateService {
    private final RedisTemplate<String, List<String>> redisTemplate;
    private final ContraindicateRepository contraindicateRepository;

//    @Transactional
//    public String findContraindicate(RequestContraindicateDto req) {
//        ValueOperations<String, List<String>> redis = redisTemplate.opsForValue();
//        List<String> list = redis.get(req.getPill_a());
//        Optional.of(list).orElse(null);
//        return list.get(1);
//    }

    @Transactional(readOnly = true)
    public String findContraindicate(String pillA, String pillB) {
        Contraindicate contraindicate = contraindicateRepository.findByPillAAndPillB(pillA, pillB).orElseThrow(
                NotFoundContraindicate::new);

        return contraindicate.getSymptom();
    }
}
