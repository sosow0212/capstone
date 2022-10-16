package com.example.capstone.factory;

import com.example.capstone.entity.member.Authority;
import com.example.capstone.entity.member.Member;

public class MemberFactory {

    public static Member createUser() {
        Member member = Member.builder()
                .username("user")
                .password("!!user123")
                .name("유저")
                .build();

        return member;
    }

}
