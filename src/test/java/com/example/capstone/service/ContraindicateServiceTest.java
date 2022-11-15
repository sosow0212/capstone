package com.example.capstone.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.capstone.entity.contraindicate.Contraindicate;
import com.example.capstone.repository.contraindicate.ContraindicateRepository;
import com.example.capstone.service.contraindicate.ContraindicateService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ContraindicateServiceTest {
    @InjectMocks
    ContraindicateService contraindicateService;

    @Mock
    ContraindicateRepository contraindicateRepository;

    @Test
    @DisplayName("병용금기 조회")
    public void findContraindicateTest() {
        // given
        String pillA = "pillA";
        String pillB = "pillB";
        Contraindicate contraindicate = new Contraindicate();
        contraindicate.setPillA(pillA);
        contraindicate.setPillB(pillB);
        contraindicate.setSymptom("테스트");
        given(contraindicateRepository.findByPillAAndPillB(pillA, pillB)).willReturn(Optional.of(contraindicate));

        // when
        String result = contraindicateService.findContraindicate(pillA, pillB);

        // then
        assertThat(result).isEqualTo("테스트");
    }
}
