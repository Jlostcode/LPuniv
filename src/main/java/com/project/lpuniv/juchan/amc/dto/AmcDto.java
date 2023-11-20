package com.project.lpuniv.juchan.amc.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AmcDto {

    int amc_no;
    int ccim_no;
    int occ_no;
    String amc_at;  //과제명(AssignmentName)
    String amc_ac;  //과제설명(AssignMentContent)


}
