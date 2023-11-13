package com.project.lpuniv.minho.listenLec.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LecInfoDto { //강의 정보 dto
    private int occ_NO;
    private String user_nm;
    private String occ_title;
    private String occ_content;
    private Date occ_date;
    private Date occ_Mdate;
    private String occ_teachInfo;
    private String occ_kakao;
}
