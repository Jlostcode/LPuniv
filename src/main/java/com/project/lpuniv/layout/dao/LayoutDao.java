package com.project.lpuniv.layout.dao;

import com.project.lpuniv.layout.dto.LayoutDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LayoutDao {
    LayoutDto findNm(int user_no);
}
