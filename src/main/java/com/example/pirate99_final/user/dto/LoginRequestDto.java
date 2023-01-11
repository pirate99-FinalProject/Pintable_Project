package com.example.pirate99_final.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequestDto {
//    @NotBlank(message = "사용자명은 필수 입력 값입니다.")
//    @Pattern(regexp = "^[0-9a-z]{4,10}$")
    private String  username;                // 사용자명

//    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String  password;               // 비밀번호
}
