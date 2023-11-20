package com.project.lpuniv.junhyuk.service;

import com.project.lpuniv.junhyuk.dao.FileDao;
import com.project.lpuniv.junhyuk.dto.FileAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileService {

    @Autowired
    FileDao fileDao;

    public void save(FileAttachment fileAttachment) {

        fileDao.save(fileAttachment);
    }

    public List<FileAttachment> findAttachmentsByPostNo(int post_no) {
        return fileDao.findAttachmentsByPostNo(post_no);
    }

    public String getOriginalFileName(String file_name) {

        return fileDao.getOriginalFileName(file_name);
    }

    public void deleteFile(String fileName, int userNo) {
        fileDao.deleteFile(fileName);
    }

}
