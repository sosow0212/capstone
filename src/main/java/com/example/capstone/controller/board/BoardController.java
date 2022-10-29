package com.example.capstone.controller.board;

import com.example.capstone.dto.board.BoardCreateReqeustDto;
import com.example.capstone.dto.board.BoardEditRequestDto;
import com.example.capstone.entity.member.Member;
import com.example.capstone.exception.MemberNotFoundException;
import com.example.capstone.repository.member.MemberRepository;
import com.example.capstone.response.Response;
import com.example.capstone.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Board Controller", tags = "Board")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "게시글 작성", notes = "게시글 작성")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/boards")
    public Response create(@Valid @RequestBody BoardCreateReqeustDto boardCreateReqeustDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        boardService.create(boardCreateReqeustDto, member);
        return Response.success();
    }

    @ApiOperation(value = "게시글 전체 조회", notes = "게시글 전체 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards")
    public Response findAll() {
        return Response.success(boardService.findAll());
    }

    @ApiOperation(value = "게시글 단건 조회", notes = "게시글 단건 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards/{boardId}")
    public Response findAll(@PathVariable("boardId") Long boardId) {
        return Response.success(boardService.find(boardId));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 수정")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/boards/{boardId}")
    public Response edit(@PathVariable("boardId") Long boardId, @Valid @RequestBody BoardEditRequestDto req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        boardService.edit(boardId, req, member);
        return Response.success();
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/boards/{boardId}")
    public Response delete(@PathVariable("boardId") Long boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        boardService.delete(boardId, member);
        return Response.success();
    }

}
