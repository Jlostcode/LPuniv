package com.project.lpuniv.minho.listenLec.dao;

import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.listenLec.dto.LecListDto;
import com.project.lpuniv.minho.listenLec.dto.LecVideoDto;
import com.project.lpuniv.minho.listenLec.dto.SchsDto;
import com.project.lpuniv.minho.student.dto.LecDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ListenLecDao {
    //강의 정보 강사 이름 강의 내용 등
    List<LecInfoDto> selectAllLitenLec();
    //학생이 듣는 강의 목록
    List<LecListDto> selectLecList(@Param("occ_NO") int occ_NO);
    //강의 영상 불러오기
    LecVideoDto selectLecVideo(@Param("ccim_NO") int ccim_NO, @Param("occ_NO") int occ_NO);
    //강의 영상 페이지 에서 수강 이력 조회
    SchsDto selectSchs(@Param("stud_no") int stud_no, @Param("occ_NO") int occ_NO,@Param("ccim_NO") int ccim_NO);
    void insertSchs(SchsDto schsDto);
    //강의 총 시간 및 시청기록 가져오기
    SchsDto selectSchsPo (int stud_no, int occ_NO, int ccim_NO);
    //강의 총 시간 및 시청기록 업데이트
    void updatePo(int stud_no, @Param("schs_fnpo") int schs_fnpo, @Param("schs_endpo") int schs_endpo ,@Param("ccim_NO") int ccim_NO, @Param("occ_NO") int occ_NO);
    List<LecDto> listenLecList(@Param("stud_no") int stud_no);
    void updateOcs(int stud_no, int occ_NO, int ccim_NO, int schs_ocs);
    Integer countCcimNo(@Param("occ_NO") int occ_NO);
    Integer countSchsOcs(@Param("stud_no") int stud_no, @Param("occ_NO") int occ_NO);
    void updateStudPg(Double stud_pg, int stud_no, int occ_NO);
    LecDto selectOneClass(int stud_no, int occ_NO);
    void updateStudSt (int stud_no, int occ_NO, Double stud_pg);
}
