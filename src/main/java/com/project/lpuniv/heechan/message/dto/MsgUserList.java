package com.project.lpuniv.heechan.message.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MsgUserList {
    private int userNo;
    private String userNm;
    private int userTp;
    private String occTitle;
}
