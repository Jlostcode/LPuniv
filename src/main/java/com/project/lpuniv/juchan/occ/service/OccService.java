package com.project.lpuniv.juchan.occ.service;

import com.project.lpuniv.juchan.occ.dao.OccDao;
import com.project.lpuniv.juchan.occ.dto.OccDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccService {

    @Autowired
    private OccDao occDao;

    public void occInsert(OccDto occDto){
        occDao.occInsert(occDto);
    }

    public Integer occAllCount(int teach_NO){
        return occDao.occAllCount(teach_NO);
    }

    public List<OccDto> occAllSelectDesc(){
       return occDao.occAllSelectDesc();
    }

    public List<OccDto> occAllSelectDescPage(int startRow, int size, int teach_NO) {
        return occDao.occAllSelectDescPage(startRow,size,teach_NO);
    };

    public OccDto occOneSelect(int occ_NO){
        return occDao.occOneSelect(occ_NO);
    }

    public void occModify(OccDto occDto) {
        occDao.occModify(occDto);
    }

    public Integer occDelete(int occ_no){
        return occDao.occDelete(occ_no);
    }
}
