package com.project.lpuniv.minho.listenLec.dao;

import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.listenLec.dto.LecListDto;
import com.project.lpuniv.minho.listenLec.dto.LecVideoDto;
import com.project.lpuniv.minho.listenLec.dto.SchsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    SchsDto selectSchs(int stud_no, int occ_NO, int ccim_NO);
    void insertSchs(SchsDto schsDto);
    //강의 총 시간 및 시청기록 업데이트
    void updatePo(int stud_no, @Param("ccim_NO") int ccim_NO, @Param("occ_NO") int occ_NO);
}
