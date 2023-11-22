package com.project.lpuniv.minho.amc.service;

import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.minho.amc.dao.AmcDaoMH;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AmcServiceMH {
    @Autowired
    AmcDaoMH amcDaoMH;

    public List<AmcDtoMH> selectAmcOccNo(int occ_NO){
        return amcDaoMH.selectAmcOccNo(occ_NO);
    }
    public AmcDtoMH selectOneAmc(int amc_no) {
        return amcDaoMH.selectOneAmc(amc_no);
    }
    public List<AmfiDto> selectOneAmfi(int amc_no) {return amcDaoMH.selectOneAmfi(amc_no);}
}
