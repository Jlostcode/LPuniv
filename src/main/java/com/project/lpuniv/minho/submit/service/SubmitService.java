package com.project.lpuniv.minho.submit.service;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.dao.SubmitDao;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
