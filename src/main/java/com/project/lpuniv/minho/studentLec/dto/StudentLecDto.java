package com.project.lpuniv.minho.studentLec.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentLecDto {
    private int stud_no;//학생번호
    private int occ_NO;//강의관리번호
    private Double stud_pg;//전체진도율
    private Double stud_sc; //과제종합점수
    private int stud_st; //수료여부
}
