package com.example.pirate99_final.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String  username;               // 사용자명
    private String  password;               // 비밀번호
    private String  email;                  // 이메일 주소
}
