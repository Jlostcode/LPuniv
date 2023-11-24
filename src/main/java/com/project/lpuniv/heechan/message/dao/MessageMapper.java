package com.project.lpuniv.heechan.message.dao;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.heechan.message.dto.Message;
import com.project.lpuniv.heechan.message.dto.MessageRequest;
import com.project.lpuniv.heechan.message.dto.MsgUserList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    int userRecMsgCnt(@Param("user_no") int userNo); //한 명의 사용자가 받은 메시지 수
    List<Message> selectUserRecMsg(@Param("startRow") int startRow, @Param("size") int size, @Param("user_no") int userNo); //한 명의 사용자가 받은 메시지
    List<Message> selectUserSenMsg(@Param("startRow") int startRow, @Param("size") int size, @Param("user_no") int userNo); //한 명의 사용자가 보낸 메시지
    List<Message> recycleMsg(@Param("startRow") int startRow, @Param("size") int size, @Param("user_no") int userNo); //휴지통
    Message selectMsg(@Param("msg_no") int msgNo); //해당하는 메시지 상세 내용
    void senDel(@Param("msg_no") int msgNo); //발신자가 메시지 삭제(DB에서는 삭제 X)
    void recDel(@Param("msg_no") int msgNo); //수신자가 메시지 삭제(DB에서는 삭제 X)
    void msgDel(@Param("msg_no") int msgNo); //발신자와 수신자 모두 메시지 삭제 했을 때 DB에서도 삭제
    void recycleRecMsg(@Param("msg_no") int msgNo); //받은 메시지 복구
    void recycleSenMsg(@Param("msg_no") int msgNo); //보낸 메시지 복구
    int msgSenCnt(@Param("user_no") int userNo); //사용자가 보낸 메시지 수
    int msgRecCnt(@Param("user_no") int userNo); //사용자가 받은 메시지 수
    int recycleMsgCnt(@Param("user_no") int userNo); //휴치동 메시지 수
    void readMsg(@Param("msg_no") int msgNo); //읽음으로 변경
    List<Message> searchRecMsg(@Param("startRow") int startRow, @Param("size") int size, @Param("user_no") int userNo, @Param("searchInput") String searchInput, @Param("searchOp") String searchOp); //검색 RecMsg검색
    List<Message> searchSenMsg(@Param("startRow") int startRow, @Param("size") int size, @Param("user_no") int userNo, @Param("searchInput") String searchInput, @Param("searchOp") String searchOp); //검색 SenMsg검색
    List<Message> searchRecycleMsg(@Param("startRow") int startRow, @Param("size") int size, @Param("user_no") int userNo, @Param("searchInput") String searchInput, @Param("searchOp") String searchOp); //검색 RecycleMsg검색
    int searchRecMsgCnt(@Param("user_no") int userNo, @Param("searchInput") String searchInput, @Param("searchOp") String searchOp); //검색 RecMsg 수
    int searchSenMsgCnt(@Param("user_no") int userNo, @Param("searchInput") String searchInput, @Param("searchOp") String searchOp); //검색 SenMsg 수
    int searchRecycleMsgCnt(@Param("user_no") int userNo, @Param("searchInput") String searchInput, @Param("searchOp") String searchOp); //검색 RecycleMsg 수
    void msgInsert(MessageRequest messageRequest, @Param("receiver_no") int receiverNo, @Param("receiver_nm") String receiverNm); //insert
    void msgUpdate(@Param("msg_no") int msgNo, String title, String content); //update
    UserDto selectByUser(@Param("user_no") int userNo);//user 정보
    List<UserDto> getUsers(@Param("user_no") int userNo); //접속한 수강생이 듣는 강의의 다른 수강생들 가져오기
    int getTeacher(@Param("user_no") int userNo); //접속한 수강생이 듣는 강의의 강사
    List<UserDto> classUsers(@Param("user_no") int userNo); //강사가 접속 했을 때 강의를 듣는 학생들
    UserDto getAdmin(); //관리자 정보 가져오기
    List<MsgUserList> userList(); //사용자 전체 가져오기(관리자가 사용)
}