package com.project.lpuniv.dayoung.user.signUp.dao;

import com.project.lpuniv.dayoung.user.signUp.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void insertUser(UserDto userDto);
    public UserDto  selectUser(int user_no);

    public List selectId(String user_LoginId);

   public UserDto selectUserByTel(String user_tel);
}
