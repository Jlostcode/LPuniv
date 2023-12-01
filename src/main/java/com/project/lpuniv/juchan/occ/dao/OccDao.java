package com.project.lpuniv.juchan.occ.dao;

import com.project.lpuniv.juchan.occ.dto.OccDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OccDao {

    void occInsert(OccDto occDto);
    Integer occAllCount(@Param("teach_NO") int teach_NO);

    List<OccDto> occAllSelectDesc();
    List<OccDto> occAllSelectDescPage(@Param("startRow") int startRow, @Param("size") int size, @Param("teach_NO") int teach_NO);

    OccDto occOneSelect(@Param("occ_NO")int occ_NO);

    void occModify(OccDto occDto);

    Integer occDelete(@Param("occ_no") int occ_no);
}
