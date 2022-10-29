package com.example.capstone.service;

import com.example.capstone.dto.sign.LoginRequestDto;
import com.example.capstone.dto.sign.SignupRequestDto;
import com.example.capstone.exception.LoginFailureException;
import com.example.capstone.repository.member.MemberRepository;
import com.example.capstone.service.sign.SignService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.capstone.factory.MemberFactory.createMember;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SignServiceUnitTest {

    @InjectMocks
    SignService signService;

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    void userSignupTest() {
        // given
        SignupRequestDto req = new SignupRequestDto("user", "user123!");

        // when
        signService.signup(req);

        // then
        verify(passwordEncoder).encode(req.getPw());
        verify(memberRepository).save(any());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void signInExceptionByNoneMemberTest() {
        // given
        given(memberRepository.findByUsername(any())).willReturn(Optional.of(createMember()));

        // when, then
        assertThatThrownBy(() -> signService.signin(new LoginRequestDto("hihi", "password")))
                .isInstanceOf(LoginFailureException.class);
    }

}
