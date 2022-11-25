package com.example.capstone.controller.authentication;

import com.example.capstone.dto.member.MemberResponseDto;
import com.example.capstone.entity.member.Member;
import com.example.capstone.exception.MemberNotFoundException;
import com.example.capstone.repository.member.MemberRepository;
import com.example.capstone.response.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final MemberRepository memberRepository;

    @ApiOperation(value = "유저 세션 조회", notes = "유저 세션 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/auth")
    public Response getMemberSession() {
        Member member = getPrincipal();
        return Response.success(MemberResponseDto.toDto(member));
    }

    public Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }
}
