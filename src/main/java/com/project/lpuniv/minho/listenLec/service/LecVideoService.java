package com.project.lpuniv.minho.listenLec.service;

import com.project.lpuniv.minho.listenLec.dao.ListenLecDao;
import com.project.lpuniv.minho.listenLec.dto.LecVideoDto;
import com.project.lpuniv.minho.listenLec.dto.SchsDto;
import com.project.lpuniv.minho.student.dto.LecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecVideoService {
    @Autowired
    ListenLecDao listenLecDao;

    public LecVideoDto selectLecVideo(int CCIM_NO, int OCC_NO) {
        return listenLecDao.selectLecVideo(CCIM_NO, OCC_NO);
    }
    public SchsDto selectSchs(int stud_no, int occ_NO, int ccim_NO) {
        return listenLecDao.selectSchs(stud_no, occ_NO, ccim_NO);
    }
    public void insertSchs(SchsDto schsDto) {
        listenLecDao.insertSchs(schsDto);
    }
    public SchsDto selectSchsPo(int stud_no, int occ_NO, int ccim_NO) {
        return listenLecDao.selectSchsPo(stud_no, occ_NO, ccim_NO);
    }
    public void updatePo(int stud_no, int occ_NO, int ccim_NO, int schs_fnpo, int schs_endpo) {
        listenLecDao.updatePo(stud_no, schs_fnpo, schs_endpo, ccim_NO, occ_NO);
    }

    public void updateOcs(int stud_no, int occ_NO, int ccim_NO, int schs_ocs) {
        listenLecDao.updateOcs(stud_no, occ_NO, ccim_NO, schs_ocs);
    }

    public void updateStudPg(Double stud_pg, int stud_no, int occ_NO){
        listenLecDao.updateStudPg(stud_pg, stud_no, occ_NO);
    }

    public LecDto selectOneClass(int stud_no, int occ_NO) {
        return listenLecDao.selectOneClass(stud_no, occ_NO);
    }

    public void updateStudSt(int stud_no, int occ_NO, Double stud_pg){
        listenLecDao.updateStudSt(stud_no, occ_NO, stud_pg);
    }
}
