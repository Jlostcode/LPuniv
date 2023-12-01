package com.project.lpuniv.juchan.amc.dao;

import com.project.lpuniv.juchan.amc.dto.AmcDto;
import com.project.lpuniv.juchan.ccim.dto.CcimDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.SimpleTimeZone;

@Mapper
public interface AmcDao {

    void amcInsert(AmcDto amcDto);   //하나의 과제 등록

    Integer amcAllCount(@Param("occ_no") int occ_no, @Param("ccim_no") int ccim_no);

    List<AmcDto> amcAllSelectDescPage(@Param("occ_no") int occ_no, @Param("ccim_no") int ccim_no, @Param("startRow") int startRow, @Param("size") int size);

    AmcDto amcOneSelect (@Param("amc_no")int amc_no);

    void amcModify (@Param("amc_at") String amc_at, @Param("amc_ac") String amc_ac, @Param("amc_no") int amc_no);

    Integer amcDelete (@Param("amc_no") int amd_no);
}
