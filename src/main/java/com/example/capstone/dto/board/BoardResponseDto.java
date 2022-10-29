package com.example.capstone.dto.board;

import com.example.capstone.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardResponseDto {
    private String title;
    private String content;
    private String username;
    private LocalDateTime createAt;

    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(board.getTitle(), board.getContent(), board.getMember().getUsername(), board.getCreatedAt());
    }
}
