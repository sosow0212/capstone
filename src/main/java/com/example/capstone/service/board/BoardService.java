package com.example.capstone.service.board;

import com.example.capstone.dto.board.BoardCreateReqeustDto;
import com.example.capstone.dto.board.BoardEditRequestDto;
import com.example.capstone.dto.board.BoardResponseDto;
import com.example.capstone.dto.board.BoardSimpleResponseDto;
import com.example.capstone.entity.board.Board;
import com.example.capstone.entity.member.Member;
import com.example.capstone.exception.BoardNotFoundException;
import com.example.capstone.exception.MemberNotEqualsException;
import com.example.capstone.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void create(BoardCreateReqeustDto req, Member member) {
        Board board = new Board(req.getTitle(), req.getContent(), member);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<BoardSimpleResponseDto> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardSimpleResponseDto> boardListDto = new ArrayList<>();
        boardList.stream().map(i -> boardListDto.add(BoardSimpleResponseDto.toDto(i))).collect(Collectors.toList());
        return boardListDto;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto find(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        BoardResponseDto boardDto = BoardResponseDto.toDto(board);
        return boardDto;
    }

    @Transactional
    public void edit(Long id, BoardEditRequestDto req, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        isWriterEqualsLoginMemberException(board, member);
        board.setTitle(req.getTitle());
        board.setContent(req.getContent());
    }

    @Transactional
    public void delete(Long id, Member member) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        isWriterEqualsLoginMemberException(board, member);
        boardRepository.delete(board);
    }

    public static void isWriterEqualsLoginMemberException(Board board, Member member) {
        if(!board.getMember().equals(member)) {
            throw new MemberNotEqualsException();
        }
    }

    // 단건 조회, 수정, 삭제
}
