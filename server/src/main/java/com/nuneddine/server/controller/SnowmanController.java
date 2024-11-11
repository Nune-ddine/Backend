package com.nuneddine.server.controller;

import com.nuneddine.server.domain.Member;
import com.nuneddine.server.dto.request.SnowmanRequestDto;
import com.nuneddine.server.dto.response.SnowmanDetailResponseDto;
import com.nuneddine.server.dto.response.SnowmanQuizResponseDto;
import com.nuneddine.server.dto.response.SnowmanResponseDto;
import com.nuneddine.server.service.JwtService;
import com.nuneddine.server.service.MemberService;
import com.nuneddine.server.service.SnowmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1") // URL 주소 매핑
public class SnowmanController {
    @Autowired
    SnowmanService snowmanService;
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberService memberService;

    // 맵 눈사람 리스트업
    @GetMapping("/map/{mapNumber}")
    public ResponseEntity<List<SnowmanResponseDto>> getSnowmansByMapNumber(@PathVariable(value = "mapNumber") int mapNumber) {
        List<SnowmanResponseDto> snowmans = snowmanService.findSnowmansByMap(mapNumber);
        if (snowmans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(snowmans);
        }
    }

    // 맵 눈사람 생성하기
    @PostMapping("/map/{mapNumber}/snowman")
    public ResponseEntity<Long> createSnowman(@RequestBody SnowmanRequestDto snowmanRequestDto, @PathVariable(value = "mapNumber") int mapNumber, @RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        Long memberId = jwtService.getMemberIdFromToken(token);
        Member member = memberService.getMemberById(memberId);

        Long snowmanId = snowmanService.createSnowman(snowmanRequestDto, mapNumber, member);
        return new ResponseEntity<>(snowmanId, HttpStatus.CREATED);
    }

    // 본인이 만든 눈사람 리스트업
    @GetMapping("/my-snowman")
    public ResponseEntity<List<SnowmanDetailResponseDto>> getMySnowman(@RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        Long memberId = jwtService.getMemberIdFromToken(token);
        Member member = memberService.getMemberById(memberId);

        List<SnowmanDetailResponseDto> snowmans = snowmanService.findMySnowman(member);
        if (snowmans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(snowmans);
        }
    }

    // 눈사람 퀴즈 보기
    @GetMapping("/snowman/{snowmanId}")
    public ResponseEntity<SnowmanQuizResponseDto> getSnowmanQuiz(@PathVariable(value = "snowmanId") Long snowmanId) {
        SnowmanQuizResponseDto snowmanQuizResponseDto = snowmanService.findSnowmanQuiz(snowmanId);
        return ResponseEntity.ok(snowmanQuizResponseDto);
    }

    // 눈사람 퀴즈 맞추기
    @PostMapping("/snowman/{snowmanId}")
    public ResponseEntity<Boolean> solveSnowmanQuiz(@PathVariable(value = "snowmanId") Long snowmanId, @RequestHeader("Authorization") String header, @RequestBody Long number) {
        String token = header.substring(7);
        Long memberId = jwtService.getMemberIdFromToken(token);
        Member member = memberService.getMemberById(memberId);

        Boolean isAnswer = snowmanService.solveSnowmanQuiz(snowmanId, number, member);
        return ResponseEntity.ok(isAnswer);
    }
}