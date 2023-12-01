package com.project.lpuniv.minho.file.dao;

import com.project.lpuniv.minho.file.dto.FileDtoMH;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileDaoMH {
    void insertFile(FileDtoMH fileDtoMH);
    List<FileDtoMH> selectFile(@Param("submit_no") int submit_no);
    FileDtoMH downloadFile(@Param("file_no") int file_no);
}
