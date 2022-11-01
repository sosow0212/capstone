package com.example.capstone.service.sign;

import com.example.capstone.config.jwt.TokenProvider;
import com.example.capstone.dto.sign.LoginRequestDto;
import com.example.capstone.dto.sign.ReissueRequestDto;
import com.example.capstone.dto.sign.SignupRequestDto;
import com.example.capstone.dto.sign.TokenResponseDto;
import com.example.capstone.dto.token.TokenDto;
import com.example.capstone.entity.member.Member;
import com.example.capstone.entity.member.RefreshToken;
import com.example.capstone.exception.LoginFailureException;
import com.example.capstone.exception.MemberUsernameAlreadyExistsException;
import com.example.capstone.repository.member.MemberRepository;
import com.example.capstone.repository.member.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignupRequestDto req) {
        validateSignUpInfo(req);
        Member member = new Member(req.getUsername(), passwordEncoder.encode(req.getPassword()));
        memberRepository.save(member);
    }

    @Transactional
    public TokenResponseDto signin(LoginRequestDto req) {
        Member member = memberRepository.findByUsername(req.getUsername()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);

        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        TokenResponseDto tokenResponseDto = new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return tokenResponseDto;
    }

    @Transactional
    public TokenResponseDto reissue(ReissueRequestDto req) {
        if (!tokenProvider.validateToken(req.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(req.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if (!refreshToken.getValue().equals(req.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        TokenResponseDto tokenResponseDto = new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return tokenResponseDto;
    }

    private void validateSignUpInfo(SignupRequestDto req) {
        if (memberRepository.existsByUsername(req.getUsername())) {
            throw new MemberUsernameAlreadyExistsException(req.getUsername());
        }
    }

    private void validatePassword(LoginRequestDto loginRequestDto, Member member) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }
}

