package com.project.lpuniv.juchan.amc.service;

import com.project.lpuniv.juchan.amc.dao.AmcDao;
import com.project.lpuniv.juchan.amc.dto.AmcDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmcService {

    @Autowired
    private AmcDao amcDao;

    public Integer amcAllCount(int occ_no, int ccim_no) {
        return amcDao.amcAllCount(occ_no,ccim_no);
    }

    public void amcInsert(AmcDto amcDto) {
        amcDao.amcInsert(amcDto);
    }

    public List<AmcDto> amcAllSelectDescPage(int occ_no, int ccim_no, int startRow, int size) {
        return amcDao.amcAllSelectDescPage(occ_no, ccim_no, startRow, size);
    }

    public AmcDto amcOneSelect(int amc_no){
        return amcDao.amcOneSelect(amc_no);
    }

    public void amcModify(String amc_at, String amc_ac, int amc_no) {
        amcDao.amcModify(amc_at,amc_ac,amc_no);
    };

    public Integer amcDelete(int amc_no) {
        return amcDao.amcDelete(amc_no);
    }
}
