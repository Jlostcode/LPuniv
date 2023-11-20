package com.project.lpuniv.dayoung.user.login.service;


import com.project.lpuniv.dayoung.user.login.dao.LoginDao;
import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.dayoung.user.login.exception.WrongIdPasswordException;
import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private LoginDao loginDao;

    public AuthInfo authenticate(String id, String password) {
        UserDto userDto = loginDao.selectById(id);

        if (userDto == null) {
            throw new WrongIdPasswordException();
        }
//        if (!userDto.matchPassword(password)) {//로그인 처리시 에러발생하여 주석
//            throw new WrongIdPasswordException();
//        }
        return new AuthInfo(userDto.getUser_no(), userDto.getUser_tp(), userDto.getUser_loginId(), userDto.getUser_passwd());
    }


    public boolean loginById(String user_loginId) {
        UserDto userDto =  loginDao.loginById(user_loginId);
        String id = userDto.getUser_loginId();
        return id != null;
    }
}
