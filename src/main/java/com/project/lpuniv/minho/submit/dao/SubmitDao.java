package com.project.lpuniv.minho.submit.dao;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubmitDao {
    UserDto selecStunm(int user_no);
    AmcDtoMH selectOneAmc(@Param(value = "amc_no") int amc_no);
    void insertSubmit(SubmitDto submitDto);
    Integer  selectSubmit();
    SubmitDto  selectSubmitAmcno(@Param("stud_no") int user_no, @Param("amc_no") int amc_no);
    List<SubmitDto> selectAllSubmit(@Param("amc_no") int amc_no);
    SubmitDto selectOneStuSubmit(@Param("amc_no") int amc_no, @Param("stud_no") int stud_no);
    void updateScore(int submit_no, int submit_sc);
}
