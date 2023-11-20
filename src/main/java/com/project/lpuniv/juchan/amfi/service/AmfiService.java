package com.project.lpuniv.juchan.amfi.service;

import com.project.lpuniv.juchan.amfi.dao.AmfiDao;
import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AmfiService {

    @Autowired
    private AmfiDao amfiDao;

    private static final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\juchan\\files\\";

    public Integer amfiAllCount(int amc_no) {
        return amfiDao.amfiAllCount(amc_no);
    }

    public List<AmfiDto> amfiAllSelectDesc(int amc_no) {
        return amfiDao.amfiAllSelectDesc(amc_no);
    };

    public AmfiDto amfiOneSelect(int amfi_no) {
        return amfiDao.amfiOneSelect(amfi_no);
    }

    public void amfiDelete(int amfi_no) {
        amfiDao.amfiDelete(amfi_no);
    }

    public void amfiInsert(int amc_no, MultipartFile file) throws IOException {

        // 파일이 업로드되었는지 확인
        if (file != null && !file.isEmpty()) {
                // 파일 처리 로직을 구현합니다.
                // 예를 들어, 파일을 저장하거나 데이터베이스에 연동하는 등의 작업을 수행합니다.
                AmfiDto amfiDto = new AmfiDto();

                UUID uuid = UUID.randomUUID();
                String fileoriginname = file.getOriginalFilename();
                String filename = uuid + "_" + file.getOriginalFilename();
                int filesize = (int) file.getSize();
                File savefile = new File(path, filename);
                file.transferTo(savefile);

                amfiDto.setAmc_no(amc_no);
                amfiDto.setAmfi_og_name(fileoriginname);
                amfiDto.setAmfi_name(filename);
                amfiDto.setAmfi_size(filesize);
                amfiDto.setAmfi_path(path + filename);
                System.out.println(amfiDto);
                amfiDao.amfiInsert(amfiDto);


        }


    }
}
