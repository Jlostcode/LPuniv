package com.project.lpuniv.juchan.occ.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OccDto {
    int occ_NO;
    int teach_NO;
    String occ_title;
    String occ_content;
    Date occ_date;
    Date occ_Mdate;
    int occ_ROT;
    String occ_teachInfo;
    String occ_kakao;
}
