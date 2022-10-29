package com.example.capstone.controller;

import com.example.capstone.controller.board.BoardController;
import com.example.capstone.dto.board.BoardCreateReqeustDto;
import com.example.capstone.dto.board.BoardEditRequestDto;
import com.example.capstone.entity.member.Member;
import com.example.capstone.repository.member.MemberRepository;
import com.example.capstone.service.board.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static com.example.capstone.factory.MemberFactory.createMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BoardControllerUnitTest {
    @InjectMocks
    BoardController boardController;

    @Mock
    BoardService boardService;

    @Mock
    MemberRepository memberRepository;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    @DisplayName("게시글 작성")
    public void createTest() throws Exception {
        // given
        BoardCreateReqeustDto req = new BoardCreateReqeustDto("제목", "내용");
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        // when
        mockMvc.perform(
                post("/api/boards")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        // then
        verify(boardService).create(req, member);
    }

    @Test
    @DisplayName("게시글 목록 조회")
    public void findAllTest() throws Exception {
        // given

        // when
        mockMvc.perform(
                get("/api/boards")
        ).andExpect(status().isOk());

        // then
        verify(boardService).findAll();
    }

    @Test
    @DisplayName("게시글 단건 조회")
    public void findTest() throws Exception {
        // given
        Long id = 1L;

        // when
        mockMvc.perform(
                get("/api/boards/{boardId}", id)
        ).andExpect(status().isOk());

        // then
        verify(boardService).find(id);
    }

    @Test
    @DisplayName("게시글 수정")
    public void editTest() throws Exception {
        // given
        Long id = 1L;
        BoardEditRequestDto req = new BoardEditRequestDto("제목", "내용");
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        // when
        mockMvc.perform(
                put("/api/boards/{boardId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isOk());

        // then
        verify(boardService).edit(id, req, member);
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteTest() throws Exception {
        // given
        Long id = 1L;
        Member member = createMember();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        // when
        mockMvc.perform(
                delete("/api/boards/{boardId}", id)
        ).andExpect(status().isOk());

        // then
        verify(boardService).delete(id, member);
    }

}
