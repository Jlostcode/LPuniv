package com.project.lpuniv.minho.submit.service;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.dao.SubmitDao;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmitService {
    @Autowired
    SubmitDao submitDao;

    public UserDto selecStunm(int user_no){
        return submitDao.selecStunm(user_no);
    }
    public AmcDtoMH selectOneAmc(int amc_no){
        return submitDao.selectOneAmc(amc_no);
    }
    public void insertSubmit(SubmitDto submitDto) {
        submitDao.insertSubmit(submitDto);
    }
    public Integer selectSubmit(){
        return submitDao.selectSubmit();
    }
    public SubmitDto selectSubmitAmcno(int user_no, int amc_no){
        return submitDao.selectSubmitAmcno(user_no, amc_no);
    }
    public List<SubmitDto> selectAllSubmit(int amc_no) {
        return submitDao.selectAllSubmit(amc_no);
    }
    public SubmitDto selectOneStuSubmit(int amc_no, int stud_no){
        return submitDao.selectOneStuSubmit(amc_no, stud_no);
    }
    public void updateScore(int submit_no, int submit_sc){
        submitDao.updateScore(submit_no, submit_sc);
    }
}
