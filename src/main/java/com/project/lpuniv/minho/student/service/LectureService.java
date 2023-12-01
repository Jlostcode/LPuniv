package com.project.lpuniv.minho.student.service;

import com.project.lpuniv.minho.student.dao.LecDao;
import com.project.lpuniv.minho.student.dto.LecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {
    @Autowired
    LecDao lecDao;

    public List<LecDto> selectAllLecPage(int startRow, int size, int occ_NO) {
        return lecDao.selectAllLecPage(startRow, size, occ_NO);
    }

    public Integer countLecPage (int occ_NO) {
        return lecDao.countLecPage(occ_NO);
    }

}
