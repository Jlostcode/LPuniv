package com.project.lpuniv.minho.listenLec.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SchsDto { //수강이력 dto
    private int schs_no;
    private Date schs_st_dt;
    private Date schs_end_dt;
    private int schs_ocs;
    private int schs_fnpo;
    private int schs_endpo;
    private Integer stud_no;
    private Integer occ_NO;
    private Integer ccim_NO;

    public SchsDto(int stud_no, int occ_NO, int ccim_NO) {
        this.stud_no = stud_no;
        this.occ_NO = occ_NO;
        this.ccim_NO = ccim_NO;
    }
}
