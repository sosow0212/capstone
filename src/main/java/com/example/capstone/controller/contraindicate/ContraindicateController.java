package com.example.capstone.controller.contraindicate;

import com.example.capstone.dto.contraindicate.RequestContraindicateDto;
import com.example.capstone.response.Response;
import com.example.capstone.service.contraindicate.ContraindicateService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ContraindicateController {

    private final ContraindicateService contraindicateService;
    private final RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "병용금기 조회", notes = "병용금기 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contraindicate")
    public Response readContraindicate(@RequestParam("pill_a") String pill_a, @RequestParam("pill_b") String pill_b) {
        return Response.success(contraindicateService.findContraindicate(pill_a, pill_b));
    }
}
