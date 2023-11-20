package com.project.lpuniv.junhyuk.dao;

import com.project.lpuniv.junhyuk.dto.FileAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileDao {

    void save(FileAttachment fileAttachment);

    List<FileAttachment> findAttachmentsByPostNo(int postNo);

    String getOriginalFileName(String file_name);

    void deleteFile(@Param("file_name") String fileName);
}
