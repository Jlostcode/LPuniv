package com.project.lpuniv.minho.studentLec.service;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.studentLec.dao.StudentLecDao;
import com.project.lpuniv.minho.studentLec.dto.StudentLecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentLecService {
    @Autowired
    StudentLecDao studentLecDao;

    public List<UserDto> stuList(){
        return studentLecDao.stuList();
    }
    public List<LecInfoDto> lecList(){
        return studentLecDao.lecList();
    }
    public List<UserDto> selectStuList(String searchSL, String searchStu){
        return studentLecDao.selectStuList(searchSL, searchStu);
    }
    public void insertClass(int stud_no, int occ_NO) {
        studentLecDao.insertClass(stud_no, occ_NO);
    }
    public StudentLecDto selectClass(int stud_no, int occ_NO) {
        return studentLecDao.selectClass(stud_no, occ_NO);
    }
    public UserDto selectUser(int stud_no) {
        return studentLecDao.selectUser(stud_no);
    }
    public LecInfoDto selectLec(int occ_NO) {
        return studentLecDao.selectLec(occ_NO);
    }
}
