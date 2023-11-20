package com.project.lpuniv.minho.studentLec.dao;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.studentLec.dto.StudentLecDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentLecDao {
    List<UserDto> stuList();
    List<LecInfoDto> lecList();
    //검색 기능
    List<UserDto> selectStuList(@Param(value = "searchSL") String searchSL, @Param(value = "searchStu") String searchStu);
    void insertClass(@Param(value = "stud_no") int stud_no, @Param(value = "occ_NO") int occ_NO);
    //체크박스 파라미터
    StudentLecDto selectClass(@Param(value = "stud_no") int stud_no, @Param(value = "occ_NO") int occ_NO);
    UserDto selectUser(@Param(value = "stud_no") int stud_no);// alert창에 나타낼 정보
    LecInfoDto selectLec(@Param(value = "occ_NO") int occ_NO);// alert창에 나타낼 정보
}
