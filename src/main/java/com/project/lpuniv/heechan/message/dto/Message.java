package com.project.lpuniv.heechan.message.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Message {
    private int msgId;
    private int senderId;
    private int receiverId;
    private String senderNm;
    private String receiverNm;
    private String title;
    private String content;
    private Date sentAt;
    private int senDel;
    private int recDel;
    private int readFlag;

    public void deleteBySender() {
        this.senDel = 1;
    }


    public void deleteByReceiver() {
        this.recDel = 1;
    }

    public boolean isDelete() {
        return (senDel == 1)&&(recDel==1);
    }
}
