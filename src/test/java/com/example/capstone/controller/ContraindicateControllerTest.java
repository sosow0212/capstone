package com.example.capstone.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.capstone.controller.contraindicate.ContraindicateController;
import com.example.capstone.service.contraindicate.ContraindicateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ContraindicateControllerTest {
    @InjectMocks
    ContraindicateController contraindicateController;

    @Mock
    ContraindicateService contraindicateService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(contraindicateController).build();
    }

    @Test
    @DisplayName("병용금기 조회")
    public void readContraindicateTest() throws Exception {
        // given
        String pill_a = "pill_a";
        String pill_b = "pill_b";

        // when
        mockMvc.perform(
                get("/contraindicate")
                        .param("pill_a", pill_a)
                        .param("pill_b", pill_b)
        ).andExpect(status().isOk());

        // then
        verify(contraindicateService).findContraindicate(pill_a, pill_b);
    }

}
