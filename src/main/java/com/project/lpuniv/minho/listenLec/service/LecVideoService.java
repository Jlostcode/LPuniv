package com.project.lpuniv.minho.listenLec.service;

import com.project.lpuniv.minho.listenLec.dao.ListenLecDao;
import com.project.lpuniv.minho.listenLec.dto.LecVideoDto;
import com.project.lpuniv.minho.listenLec.dto.SchsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecVideoService {
    @Autowired
    ListenLecDao listenLecDao;

    public LecVideoDto selectLecVideo(int ccim_NO, int occ_NO) {
        return listenLecDao.selectLecVideo(ccim_NO, occ_NO);
    }
    public SchsDto selectSchs(int stud_no) {
        return listenLecDao.selectSchs(stud_no);
    }
    public void insertSchs(SchsDto schsDto) {
        listenLecDao.insertSchs(schsDto);
    }
}
