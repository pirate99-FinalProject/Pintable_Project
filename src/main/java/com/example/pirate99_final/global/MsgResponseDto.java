package com.example.pirate99_final.global;

import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.global.exception.SuccessCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@NoArgsConstructor
public class MsgResponseDto {
    private int statusCode;
    private String msg;

    public MsgResponseDto(SuccessCode successCode) {
        this.msg = successCode.getMessage();
        this.statusCode = successCode.getHttpStatus().value();
    }

    public MsgResponseDto(int statusCode, String msg) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public MsgResponseDto(CustomException customException) {

        this.statusCode = customException.getStatusCode();

        this.msg = customException.getMsg();
    }
    public MsgResponseDto(MethodArgumentNotValidException ex) {
        this.msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }
    public MsgResponseDto(ErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.msg = errorCode.getMessage();
    }
}
