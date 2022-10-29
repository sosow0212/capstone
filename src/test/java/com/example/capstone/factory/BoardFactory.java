package com.example.capstone.factory;

import com.example.capstone.entity.board.Board;
import com.example.capstone.entity.member.Member;

public class BoardFactory {

    public static Board createBoard(Member member) {
        return new Board("제목", "내용", member);
    }
}
