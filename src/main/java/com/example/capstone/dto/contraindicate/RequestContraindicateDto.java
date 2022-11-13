package com.example.capstone.dto.contraindicate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "병용금기 검색")
public class RequestContraindicateDto {
    @ApiModelProperty(value = "의약품1", notes = "의약품의 이름을 입력해주세요.", required = true, example = "타이레놀")
    @NotBlank(message = "의약품의 이름을 입력해주세요.")
    private String pill_a;

    @ApiModelProperty(value = "의약품1", notes = "의약품의 이름을 입력해주세요.", required = true, example = "타이레놀")
    @NotBlank(message = "의약품의 이름을 입력해주세요.")
    private String pill_b;
}
