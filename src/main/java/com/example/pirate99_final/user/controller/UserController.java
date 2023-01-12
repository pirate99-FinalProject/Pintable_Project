package com.example.pirate99_final.user.controller;


import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.user.dto.LoginRequestDto;
import com.example.pirate99_final.user.dto.SignupRequestDto;
import com.example.pirate99_final.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public MsgResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        return new MsgResponseDto(SuccessCode.SIGN_UP);
    }

    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        //클라이언트에 반환하기 위해 response 객체
        userService.login(loginRequestDto);

        return new MsgResponseDto(SuccessCode.LOG_IN);
    }

}
