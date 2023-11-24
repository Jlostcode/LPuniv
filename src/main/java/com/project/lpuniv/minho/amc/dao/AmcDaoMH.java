package com.project.lpuniv.minho.amc.dao;

import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmcDaoMH {
    List<AmcDtoMH> selectAmcOccNo(@Param(value = "occ_NO") int occ_NO);
    AmcDtoMH selectOneAmc(@Param(value = "amc_no") int amc_no);
    List<AmfiDto> selectOneAmfi(@Param(value = "amc_no") int amc_no);
    void insertSubmit(SubmitDto submitDto);
}
