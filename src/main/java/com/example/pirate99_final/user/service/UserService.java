package com.example.pirate99_final.user.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.user.dto.LoginRequestDto;
import com.example.pirate99_final.user.dto.SignupRequestDto;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.pirate99_final.global.exception.SuccessCode.LOG_IN;
import static com.example.pirate99_final.global.exception.SuccessCode.SIGN_UP;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {

        // 1. USERNAME, PASSWORD SETTING
        String username = signupRequestDto.getUsername();                                           // username setting (DTO ->  val)
        String password = signupRequestDto.getPassword();                                           // password setting (DTO ->  val)

        // 2. find user (duplicate user)
        Optional<User> found = userRepository.findByUsername(username);                             // 회원 중복 확인
        if (found.isPresent()) {                                                                    // isPresent - > found가 null이 아니라면 true 반환
            throw new CustomException(ErrorCode.DUPLICATE_USER_ERROR);
        }

        // 5. DB insert
        User user = new User(username, password);                                             // DTO -> Entity
        userRepository.save(user);
        return new MsgResponseDto(SIGN_UP);
    }

    // 로그인
    @Transactional(readOnly = true)
    public MsgResponseDto login(LoginRequestDto loginRequestDto) {

        // 1. USERNAME, PASSWORD SETTING
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 2. Check USERNAME, PASSWORD
        User user = userRepository.findByUsername(username).orElseThrow(                            // 사용자 확인
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        if(password.equals(user.getPassword())){                                 // 비밀번호 비교
            throw  new CustomException(ErrorCode.WRONG_PASSWORD_ERROR);
        }

        return new MsgResponseDto(LOG_IN);
    }
}
