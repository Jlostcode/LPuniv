package com.project.lpuniv.minho.submit.dao;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubmitDao {
    UserDto selecStunm(int user_no);
    AmcDtoMH selectOneAmc(@Param(value = "amc_no") int amc_no);
    void insertSubmit(SubmitDto submitDto);
}
