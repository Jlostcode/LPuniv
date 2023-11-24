package com.project.lpuniv.dayoung.user.signUp.dao;

import com.project.lpuniv.dayoung.user.signUp.dto.ListDto;
import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    void updateUser(ListDto data);
//    List<ListDto> updateUser(ListDto data);

    void deleteDate(int user_no);

  void modifySelf(int user_no,String user_tel);
  void resetPw(int user_no);
  void delUser(int user_no);

void updateUserPw(@Param("user_no") int user_no,@Param("user_pw") String user_passwd);

}
