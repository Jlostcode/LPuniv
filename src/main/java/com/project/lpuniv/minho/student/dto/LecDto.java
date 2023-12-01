package com.project.lpuniv.minho.student.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LecDto {
    //학생번호(FK 이름 가져올 때)
    private int stud_no;

    //강의 관리번호
    private int occ_NO;

    //수업 전체 진도율
    private double stud_pg;

    //학생 수료 여부
    private int stud_st;
    private String occ_title;
    private String stud_nm;

//    public LecDto(int stud_no, int occ_NO, double stud_pg, double stud_sc, int stud_St) {
//        this.stud_no = stud_no;
//        this.occ_NO = occ_NO;
//        this.stud_pg = stud_pg;
//        this.stud_sc = stud_sc;
//        this.stud_St = stud_St;
//    }
}

//한 강사의 강의에 들어가면 학생 등록 버튼
//그 버튼 누르면 학생 리스트 엑셀 파일로 등록
//수업 전체 진도율: 수료 여부 80% , 총 시간에서 영상 100% 들은 수 카운트 둘이 나눠서 80% 넘으면 수료

