package com.example.pirate99_final.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SIGN_UP(HttpStatus.OK, "회원가입에 성공했습니다."),
    LOG_IN(HttpStatus.OK, "로그인에 성공했습니다"),
    CREATE_WAITING(HttpStatus.OK, "대기자 명단 등록 성공"),
    DELETE_WAITING(HttpStatus.OK, "대기자 명단 삭제 성공"),
    CREATE_STORE(HttpStatus.OK, "점포 등록 성공"),
    DELETE_STORE(HttpStatus.OK, "점포 삭제 성공"),
    CREATE_REVIEW(HttpStatus.OK, "리뷰 등록 성공"),
    DELETE_REVIEW(HttpStatus.OK, "리뷰 삭제 성공"),
    CONFIRM_ENTER(HttpStatus.OK, "입장 확인"),
    CONFIRM_LEAVE(HttpStatus.OK, "퇴장 확인"),

    CALL_PEOPLE(HttpStatus.OK, "대기자를 호출했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}