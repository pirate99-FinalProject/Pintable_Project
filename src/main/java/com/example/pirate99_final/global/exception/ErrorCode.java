package com.example.pirate99_final.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_ID_ERROR(HttpStatus.OK, "해당하는 ID가 없습니다."),

    NOT_FOUND_USER_ERROR(HttpStatus.OK, "해당하는 사용자를 찾을 수 없습니다."),
    DUPLICATE_USER_ERROR(HttpStatus.OK, "이미 존재하는 회원입니다."),

    WRONG_PASSWORD_ERROR(HttpStatus.OK, "잘못된 비밀번호입니다."),
    NOT_FOUND_STORE_ERROR(HttpStatus.OK, "해당하는 상호명이 없습니다."),

    NOT_FOUND_REVIEW_ERROR(HttpStatus.OK, "해당하는 리뷰가 없습니다."),

    ALREADY_IN_QUEUE(HttpStatus.OK, "이미 대기열에 존재합니다."),

    ALREADY_LEAVING(HttpStatus.OK, "이미 퇴장한 손님입니다."),

    ALREADY_EATING(HttpStatus.OK, "이미 식사 중인 손님입니다."),
    LIMIT_QUEUE_EXCEEDED(HttpStatus.OK, "제한 대기열 초과");


    private final HttpStatus httpStatus;
    private final String message;
}
