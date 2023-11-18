package com.project.lpuniv.dayoung.user.login.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private int user_no;
    private Integer user_tp;
    private String user_nm;
    private String user_tel;
    private String user_loginId;
    private String user_passwd;
    private String user_email;
    private String user_brdt;
    private Integer user_gen;
    private String user_signdate;
    private String user_deletedate;


}
