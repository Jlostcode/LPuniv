package com.project.lpuniv.minho.submit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitDto {
    private int submit_no;
    private int stud_no;
    private int occ_NO;
    private int amc_no;
    private String submit_ct;
    private int submit_att;
    private Double submit_sc;
}
