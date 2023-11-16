package com.project.lpuniv.dayoung.user.login.service;

import com.project.lpuniv.dayoung.user.login.dao.LoginDao;
import com.project.lpuniv.dayoung.user.signUp.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoginService {
    @Autowired
    LoginDao loginDao;
    public List<String> getIdList(String term) {
        // 마이바티스를 사용하여 사용자 아이디 목록을 검색하는 메서드 호출
        return loginDao.selectId(term);
    }
}
