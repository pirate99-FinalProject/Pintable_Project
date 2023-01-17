package com.example.pirate99_final.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailSendDto {
    private String address;
    private String title;
    private String content;
}
