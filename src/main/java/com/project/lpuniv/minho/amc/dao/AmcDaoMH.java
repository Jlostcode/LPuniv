package com.project.lpuniv.minho.amc.dao;

import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmcDaoMH {
    List<AmcDtoMH> selectAmcOccNo(@Param(value = "occ_NO") int occ_NO);
}
