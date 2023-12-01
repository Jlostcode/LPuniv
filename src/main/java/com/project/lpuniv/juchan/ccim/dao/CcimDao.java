package com.project.lpuniv.juchan.ccim.dao;

import com.project.lpuniv.juchan.ccim.dto.CcimDto;
import com.project.lpuniv.juchan.occ.dto.OccDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CcimDao {

    void ccimInsert(CcimDto ccimDto);   //하나의 챕터 등록

    Integer ccimAllCount(@Param("occ_NO") int occ_NO);  //한 강의 챕터 갯수 카운트

    List<CcimDto> ccimAllSelectDesc();  // 챕터 모두 조회 내림차순

    CcimDto ccimOneSelect(@Param("ccim_NO")int ccim_NO);    // 한 챕터 조회

    List<CcimDto> ccimAllSelectDesc(@Param("occ_NO") int occ_NO);   // 한 강의 챕터 조회 내림차순

    // 한 강의 챕터 조회 내림차순 페이징
    List<CcimDto> ccimAllSelectDescPage(@Param("occ_NO") int occ_NO, @Param("startRow") int startRow, @Param("size") int size);

    void ccimModify(CcimDto ccimDto);   // 하나의 챕터 수정

    Integer ccimDelete(@Param("ccim_no") int ccim_no);
}
