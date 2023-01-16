package com.example.pirate99_final.user.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.user.dto.LoginRequestDto;
import com.example.pirate99_final.user.dto.SignupRequestDto;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

import static com.example.pirate99_final.global.exception.SuccessCode.LOG_IN;
import static com.example.pirate99_final.global.exception.SuccessCode.SIGN_UP;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 사용자 이메일 유효성 체크 기능 관련
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private String authNum;

    // 회원가입
    @Transactional
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {

        // 1. USERNAME, PASSWORD SETTING
        String username = signupRequestDto.getUsername();                                                               // username setting (DTO ->  val)
        String password = signupRequestDto.getPassword();                                                               // password setting (DTO ->  val)
        String address  = signupRequestDto.getAddress();

        // 2. find user (duplicate user)
        Optional<User> found = userRepository.findByUsername(username);                                                 // 회원 중복 확인
        if (found.isPresent()) {                                                                                        // isPresent - > found가 null이 아니라면 true 반환
            throw new CustomException(ErrorCode.DUPLICATE_USER_ERROR);
        }

        // 5. DB insert
        User user = new User(username, password, address);                                                                       // DTO -> Entity
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
        User user = userRepository.findByUsername(username).orElseThrow(                                                // 사용자 확인
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        if (!password.equals(user.getPassword())) {                                                                     // 비밀번호 비교
            throw new CustomException(ErrorCode.WRONG_PASSWORD_ERROR);
        }

        return new MsgResponseDto(LOG_IN);
    }


    // 사용자 이메일 유효성 체크 기능 - 1. 인증코드 생성
    public String authNum(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        return authNum = key.toString();
    }

    // 사용자 이메일 유효성 체크 기능 - 2. 이메일 전송 폼 생성
    public MimeMessage createEmailForm(String sendmail) throws MessagingException {
        authNum = authNum();
        String setFrom = "pintable99@gmail.com";                                                                        // application-properties에 설정된 보내는 메일 주소 이름
        String toEmail = sendmail;                                                                                      // 수신인
        String title = "Pin Table 회원가입 인증 번호";                                                                       // 제목

        MimeMessage message = emailSender.createMimeMessage();                                                          // JavaMailSender를 이용한 이메일 작성
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(title);
        message.setFrom(setFrom);
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    // 사용자 이메일 유효성 체크 기능 - 3. Thymeleaf를 이용하여 동적 렌더링 구현
    private String setContext(String authNum) {
        Context context = new Context();
        context.setVariable("code", authNum);
        return templateEngine.process("mailForm", context); //mail.html
    }


    // 사용자 이메일 유효성 체크 기능 - 4. 이메일 전송
    public String sendEmail(String toEmail) throws MessagingException {


        MimeMessage emailForm = createEmailForm(toEmail);                                                               // 메일전송에 필요한 정보 설정

        emailSender.send(emailForm);                                                                                    // 메일 전송

        return authNum;                                                                                                 //인증 코드 반환
    }




}
