package com.project.lpuniv.heechan.message.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private int msgNo;
    private int senderNo;
    private int receiverNo;
    private String senderNm;
    private String receiverNm;
    private String title;
    private String content;
    private LocalDateTime sentAt;
    private int senDel;
    private int recDel;
    private int readFlag;

    public Message(int senderNo, String senderNm, int receiverNo, String receiverNm, String title, String content) {
        this.senderNo = senderNo;
        this.receiverNo = receiverNo;
        this.senderNm = senderNm;
        this.receiverNm = receiverNm;
        this.title = title;
        this.content = content;
    }
}
