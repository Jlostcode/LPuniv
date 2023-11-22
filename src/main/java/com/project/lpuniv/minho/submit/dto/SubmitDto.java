package com.project.lpuniv.minho.submit.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubmitDto {
    private int submit_no;
    private int stud_no;
    private int occ_NO;
    private int amc_no;
    private String submit_ct;
    private Double submit_sc;
    private String stud_nm;
}
