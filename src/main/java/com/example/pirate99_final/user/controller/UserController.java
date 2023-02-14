package com.example.pirate99_final.user.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.user.dto.LoginRequestDto;
import com.example.pirate99_final.user.dto.MailConfirmRequestDto;
import com.example.pirate99_final.user.dto.SignupRequestDto;
import com.example.pirate99_final.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public MsgResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        //클라이언트에 반환하기 위해 response 객체
        userService.login(loginRequestDto);

        return userService.login(loginRequestDto);
    }

    // 사용자 메일 유효성 체크 기능
    @PostMapping("/login/mailConfirm")
    public String mailConfirm(@RequestBody MailConfirmRequestDto mailAddress) throws MessagingException {
        String authCode = userService.sendEmail(mailAddress.getEmail());
        return authCode;
    }

}
