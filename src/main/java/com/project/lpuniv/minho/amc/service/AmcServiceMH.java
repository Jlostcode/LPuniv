package com.project.lpuniv.minho.amc.service;

import com.project.lpuniv.minho.amc.dao.AmcDaoMH;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmcServiceMH {
    @Autowired
    AmcDaoMH amcDaoMH;

    public List<AmcDtoMH> selectAmcOccNo(int occ_NO){
        return amcDaoMH.selectAmcOccNo(occ_NO);
    }
}
