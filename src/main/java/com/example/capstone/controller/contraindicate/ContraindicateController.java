package com.example.capstone.controller.contraindicate;

import com.example.capstone.response.Response;
import com.example.capstone.service.contraindicate.ContraindicateService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/java")
public class ContraindicateController {
    private final ContraindicateService contraindicateService;

    @ApiOperation(value = "병용금기 조회", notes = "병용금기 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contraindicate")
    public Response readContraindicate(@RequestParam("pill_a") String pill_a, @RequestParam("pill_b") String pill_b) {
        return Response.success(contraindicateService.findContraindicate(pill_a, pill_b));
    }
}
