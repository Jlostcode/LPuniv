package com.project.lpuniv.minho.file.dao;

import com.project.lpuniv.minho.file.dto.FileDtoMH;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileDaoMH {
    void insertFile(FileDtoMH fileDtoMH);
    FileDtoMH selectFile(@Param("submit_no") int submit_no);
}
