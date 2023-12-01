package com.project.lpuniv.minho.student.dao;

import com.project.lpuniv.minho.student.dto.LecDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LecDao {
    //@return 리스트 조회
    List<LecDto> selectAllLecPage(@Param("startRow") int startRow, @Param("size") int size, @Param("occ_NO") int occ_NO);
    //리스트 수 카운팅
    Integer countLecPage(@Param("occ_NO") int occ_NO);
    LecDto selectStuNo(int stud_no);

}
