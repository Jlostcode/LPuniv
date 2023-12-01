package com.project.lpuniv.minho.file.service;

import com.project.lpuniv.minho.file.dao.FileDaoMH;
import com.project.lpuniv.minho.file.dto.FileDtoMH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceMH {
    @Autowired
    FileDaoMH fileDaoMH;
    
    private static final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\minho\\files\\";//저장될 디렉토리 생성및 설정

    public void insertFile(MultipartFile file, int submit_no) throws IOException {
        if (file != null && !file.isEmpty()) {
            // 파일 처리 로직을 구현합니다.
            // 예를 들어, 파일을 저장하거나 데이터베이스에 연동하는 등의 작업을 수행합니다.
            FileDtoMH fileDtoMH = new FileDtoMH();
            UUID uuid = UUID.randomUUID();
            String fileoriginname = file.getOriginalFilename();
            String filename = uuid + "_" + file.getOriginalFilename();
            int filesize = (int) file.getSize();
            File savefile = new File(path, filename);
            file.transferTo(savefile);//세이브 파일 경로에 저장하라는 명령어

            fileDtoMH.setSubmit_no(submit_no);
            fileDtoMH.setFile_uuid(filename);
            fileDtoMH.setFile_path(path + filename);
            fileDtoMH.setFile_nm(fileoriginname);
            fileDtoMH.setFile_cp(filesize);
            System.out.println("fileDtoMH============="+fileDtoMH);

            fileDaoMH.insertFile(fileDtoMH);
        }
    }
    public List<FileDtoMH> selectFile(int submit_no){
        return fileDaoMH.selectFile(submit_no);
    }
    public FileDtoMH downloadFile(int file_no) {
        return fileDaoMH.downloadFile(file_no);
    }
}
