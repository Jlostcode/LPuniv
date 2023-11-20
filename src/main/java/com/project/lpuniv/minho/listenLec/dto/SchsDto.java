package com.project.lpuniv.minho.listenLec.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchsDto { //수강이력 dto
    private int schs_no;
    private Date schs_st_dt;
    private Date schs_end_dt;
    private int shcs_ocs;
    private int schs_fnpo;
    private int schs_endpo;
    private int stud_no;
    private int occ_NO;
    private int ccim_NO;
}
