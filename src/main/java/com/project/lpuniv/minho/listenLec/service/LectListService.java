package com.project.lpuniv.minho.listenLec.service;

import com.project.lpuniv.minho.listenLec.dao.ListenLecDao;
import com.project.lpuniv.minho.listenLec.dto.LecListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectListService {
    @Autowired
    ListenLecDao listenLecDao;

    public List<LecListDto> selectLecList(int occ_NO) {
        return listenLecDao.selectLecList(occ_NO);
    }
}
