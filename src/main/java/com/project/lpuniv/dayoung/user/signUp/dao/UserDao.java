package com.project.lpuniv.dayoung.user.signUp.dao;

import com.project.lpuniv.Dto;
import com.project.lpuniv.dayoung.user.signUp.dto.ListDto;
import com.project.lpuniv.dayoung.user.signUp.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void insertUser(UserDto userDto);
    public UserDto  selectUser(int user_no);

    public List selectId(String user_loginId);

    int countUser();
    List<ListDto> userList(int startRow, int size);

   public UserDto selectUserByTel(String user_tel);

    List<ListDto> serchList (String serchFind, String selectOption,int startRow, int size);
}
