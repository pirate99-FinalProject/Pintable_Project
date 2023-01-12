package com.example.pirate99_final.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SIGN_UP_ERROR(HttpStatus.BAD_REQUEST, "회원가입에 실패했습니다."),
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "로그인에 실패했습니다."),
    WAITING_POST_ERROR(HttpStatus.BAD_REQUEST, "대기자 명단 등록에 실패했습니다."),
    WAITING_DELETE_ERROR(HttpStatus.BAD_REQUEST, "대기자 명단 삭제에 실패했습니다."),  // error code 대신 status code 200과 error message 를 함께 보낸다.
    STORE_POST_ERROR(HttpStatus.BAD_REQUEST, "점포 등록에 실패했습니다."),
    STORE_DELETE_ERROR(HttpStatus.BAD_REQUEST, "점포 삭제에 실패했습니다."),
    REVIEW_POST_ERROR(HttpStatus.BAD_REQUEST, "리뷰 등록에 실패했습니다."),
    NOT_FOUND_STORE_STATUS_ERROR(HttpStatus.BAD_REQUEST, "해당하는 상호 상태가 없습니다."),
    REVIEW_DELETE_ERROR(HttpStatus.OK, "리뷰 삭제에 실패했습니다."),
    NOT_FOUND_REVIEW_ERROR(HttpStatus.OK, "해당하는 리뷰가 없습니다."),
    NOT_FOUND_ID_ERROR(HttpStatus.OK, "해당하는 ID가 없습니다."),
    NOT_FOUND_STORE_ERROR(HttpStatus.OK, "해당하는 상호명이 없습니다."),
    NOT_FOUND_USER_ERROR(HttpStatus.OK, "해당하는 사용자를 찾을 수 없습니다."),
    DUPLICATE_USER_ERROR(HttpStatus.OK, "이미 존재하는 회원입니다."),
    WRONG_PASSWORD_ERROR(HttpStatus.OK, "잘못된 비밀번호입니다."),
    NOT_ENOUGH_TABLE(HttpStatus.OK, "빈 자리가 없습니다. 예약 등록 부탁드립니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
