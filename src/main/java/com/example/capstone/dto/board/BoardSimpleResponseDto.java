package com.example.capstone.dto.board;

import com.example.capstone.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class BoardSimpleResponseDto {
    private String title;
    private String username;

    public static BoardSimpleResponseDto toDto(Board board) {
        return new BoardSimpleResponseDto(board.getTitle(), board.getContent());
    }
}

