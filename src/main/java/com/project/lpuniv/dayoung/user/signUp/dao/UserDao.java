package com.project.lpuniv.dayoung.user.signUp.dao;

import com.project.lpuniv.dayoung.user.signUp.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    void insertUser(UserDto userDto);

}
