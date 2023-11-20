package com.project.lpuniv.juchan.ccim.dto;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CcimDto {

    int ccim_NO;
    int occ_NO;
    String ccim_title;
    String ccim_content;
    String ccim_videoID;
    Time ccim_rt;


}
