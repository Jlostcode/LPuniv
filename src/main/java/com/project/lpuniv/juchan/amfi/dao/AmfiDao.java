package com.project.lpuniv.juchan.amfi.dao;

import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AmfiDao {

    void amfiInsert(AmfiDto amfiDto);   //하나의 파일 등록

    Integer amfiAllCount(int amc_no);

    List<AmfiDto> amfiAllSelectDesc(int amc_no);

    AmfiDto amfiOneSelect(int amfi_no);

    void amfiDelete(int amfi_no);
}
