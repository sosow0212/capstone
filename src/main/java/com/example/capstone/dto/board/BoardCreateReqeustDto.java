package com.example.capstone.dto.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "게시글 생성")
public class BoardCreateReqeustDto {

    @ApiModelProperty(value = "제목", notes = "게시글 제목을 입력해주세요.", required = true, example = "게시글 제목입니다.")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "내용", notes = "게시글 내용을 입력해주세요.", required = true, example = "게시글 내용입니다.")
    @NotBlank(message = "게시글 내용을 입력해주세요.")
    private String content;
}
