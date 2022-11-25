package com.example.capstone.dto.member;

import com.example.capstone.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String username;

    public static MemberResponseDto toDto(Member member) {
        return new MemberResponseDto(member.getId(), member.getUsername());
    }
}
