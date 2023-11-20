package com.project.lpuniv.heechan.message.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageRequest {
    private int senderNo;
    private String senderNm;
    private List<Receiver> receivers;
    private String title;
    private String content;
}

