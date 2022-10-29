package com.example.capstone.service;

import com.example.capstone.dto.board.BoardCreateReqeustDto;
import com.example.capstone.dto.board.BoardEditRequestDto;
import com.example.capstone.dto.board.BoardResponseDto;
import com.example.capstone.dto.board.BoardSimpleResponseDto;
import com.example.capstone.entity.board.Board;
import com.example.capstone.entity.member.Member;
import com.example.capstone.repository.board.BoardRepository;
import com.example.capstone.service.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.capstone.factory.BoardFactory.createBoard;
import static com.example.capstone.factory.MemberFactory.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTest {
    @InjectMocks
    BoardService boardService;

    @Mock
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 생성")
    public void createTest() {
        // given
        Member member = createMember();
        BoardCreateReqeustDto req = new BoardCreateReqeustDto("제목", "내용");

        // when
        boardService.create(req, member);

        // then
        verify(boardRepository).save(any());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    public void findAllTest() {
        // given
        Member member = createMember();
        List<Board> boardList = List.of(createBoard(member), createBoard(member));
        given(boardRepository.findAll()).willReturn(boardList);

        // when
        List<BoardSimpleResponseDto> result = boardService.findAll();

        // then
        assertThat(result.size()).isEqualTo(boardList.size());
    }

    @Test
    @DisplayName("게시글 단건 조회")
    public void findTest() {
        // given
        Long id = 1L;
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findById(id)).willReturn(Optional.of(board));

        // when
        BoardResponseDto result = boardService.find(id);

        // then
        assertThat(result.getTitle()).isEqualTo(board.getTitle());
    }

    @Test
    @DisplayName("게시글 수정")
    public void editTest() {
        // given
        Long id = 1L;
        BoardEditRequestDto req = new BoardEditRequestDto("제목2", "내용2");
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findById(id)).willReturn(Optional.of(board));

        // when
        boardService.edit(id, req, member);

        // then
        assertThat(board.getTitle()).isEqualTo("제목2");
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteTest() {
        // given
        Long id = 1L;
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findById(id)).willReturn(Optional.of(board));

        // when
        boardService.delete(id, member);

        // then
        verify(boardRepository).delete(board);
    }
}
