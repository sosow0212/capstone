package com.example.capstone.factory;

import com.example.capstone.entity.member.Authority;
import com.example.capstone.entity.member.Member;

public class MemberFactory {

    public static Member createMember() {
        Member member = Member.builder()
                .username("user")
                .pw("!!user123")
                .build();

        return member;
    }

}
