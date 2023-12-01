package com.project.lpuniv.minho.listenLec.service;

import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.listenLec.dao.ListenLecDao;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.student.dto.LecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecInfoService {
    @Autowired
    ListenLecDao listenLecDao;

    public List<LecInfoDto> selectAllLitenLec() {
        return listenLecDao.selectAllLitenLec();
    }
    public List<LecDto> listenLecList(int stud_no){
        return listenLecDao.listenLecList(stud_no);
    }
}
