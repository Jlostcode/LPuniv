package com.project.lpuniv.juchan.ccim.service;

import com.project.lpuniv.juchan.ccim.dao.CcimDao;
import com.project.lpuniv.juchan.ccim.dto.CcimDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CcimService {

    @Autowired
    private CcimDao ccimDao;

    public void ccimInsert(CcimDto ccimDto){
        ccimDao.ccimInsert(ccimDto);
    }

    public Integer ccimAllCount(int occ_NO){
        return ccimDao.ccimAllCount(occ_NO);
    }

    public List<CcimDto> ccimAllSelectDesc(int occ_NO) { return ccimDao.ccimAllSelectDesc(occ_NO);}

    public List<CcimDto> ccimAllSelectDescPage(int occ_NO, int startRow, int size) {
        return ccimDao.ccimAllSelectDescPage(occ_NO,startRow,size);
    };

    public CcimDto ccimOneSelect(int ccim_NO){
        return ccimDao.ccimOneSelect(ccim_NO);
    }

    public void ccimModify(CcimDto ccimDto) {
        ccimDao.ccimModify(ccimDto);
        ccimDao.ccimModify(ccimDto);
    }

    public Integer ccimDelete(int ccim_no) {
        return ccimDao.ccimDelete(ccim_no);
    }
}
