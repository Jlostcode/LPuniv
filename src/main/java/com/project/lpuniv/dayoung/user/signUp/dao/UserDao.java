package com.project.lpuniv.dayoung.user.signUp.dao;

import com.project.lpuniv.dayoung.user.signUp.dto.ListDto;
import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void insertUser(SignupDto signupDto);
    public SignupDto selectUser(int user_no);

    public List selectId(String user_loginId);

    List<ListDto>gridList();
    int countUser();
    List<ListDto> userList(int startRow, int size);

   public SignupDto selectUserByTel(String user_tel);

    List<ListDto> serchList (String serchFind, String selectOption,int startRow, int size);

    List<ListDto>updateUser(ListDto data);

}
