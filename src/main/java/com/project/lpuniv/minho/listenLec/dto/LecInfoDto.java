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
public class LecInfoDto {
    private int occ_NO;
    private String user_nm;
    private String OCC_title;
    private String OCC_content;
    private Date OCC_date;
    private Date OCC_Mdate;
    private String OCC_teachInfo;
    private String OCC_kakao;
}
