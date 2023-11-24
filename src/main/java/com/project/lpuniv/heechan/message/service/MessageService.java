package com.project.lpuniv.heechan.message.service;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.heechan.message.dao.MessageMapper;
import com.project.lpuniv.heechan.message.dto.Message;
import com.project.lpuniv.heechan.message.dto.MessageRequest;
import com.project.lpuniv.heechan.message.dto.MsgUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public int userRecMsgCnt(int userNo) { //한 명의 사용자가 받은 메시지 수
        return messageMapper.userRecMsgCnt(userNo);
    }

    public List<Message> selectUserRecMsg(int startRow, int size, int userNo) { //한 명의 사용자가 받은 메시지
        return messageMapper.selectUserRecMsg(startRow, size, userNo);
    }

    public List<Message> selectUserSenMsg(int startRow, int size, int userNo){ //한 명의 사용자가 보낸 메시지
        return messageMapper.selectUserSenMsg(startRow, size, userNo);
    }

    public List<Message> recycleMsg(int startRow, int size, int userNo){
        return messageMapper.recycleMsg(startRow, size, userNo);
    }

    public Message selectMsg(int msgNo){ //해당하는 메시지 상세 내용
        return messageMapper.selectMsg(msgNo);
    }

    public void senDel(int msgNo) { //발신자가 메시지 삭제(DB에서는 삭제 X)
        messageMapper.senDel(msgNo);
    }

    public void recDel(int msgNo) { //수신자가 메시지 삭제(DB에서는 삭제 X)
        messageMapper.recDel(msgNo);
    }

    public void msgDel(int msgNo) { //발신자와 수신자 모두 메시지 삭제 했을 때 DB에서도 삭제
        messageMapper.msgDel(msgNo);
    }

    public void recycleRecMsg(int msgNo) { //받은 메시지 복구
        messageMapper.recycleRecMsg(msgNo);
    }

    public void recycleSenMsg(int msgNo) { //보낸 메시지 복구
        messageMapper.recycleSenMsg(msgNo);
    }

    public int msgSenCnt(int userNo) { //사용자가 보낸 메시지 수
        return messageMapper.msgSenCnt(userNo);
    }

    public int msgRecCnt(int userNo) { //사용자가 받은 메시지 수
        return messageMapper.msgRecCnt(userNo);
    }

    public int recycleMsgCnt(int userNo) { //휴지통 메시지 수
        return messageMapper.recycleMsgCnt(userNo);
    }

    public void readMsg(int msgNo) { //읽음으로 변경
        messageMapper.readMsg(msgNo);
    }

    public List<Message> searchMsg(int startRow, int size, int userNo, String searchInput, String searchOp, String div) { //검색
        String searchinput = '%' + searchInput + '%';
        if(div.equals("rec")){
            return messageMapper.searchRecMsg(startRow, size, userNo, searchinput, searchOp);
        } else if(div.equals("sen")){
            return messageMapper.searchSenMsg(startRow, size, userNo, searchinput, searchOp);
        } else {
            return messageMapper.searchRecycleMsg(startRow, size, userNo, searchinput, searchOp);
        }
    }

    public int searchMsgCnt(int userNo, String searchInput, String searchOp, String div) { //검색 메시지 수
        String searchinput = '%' + searchInput + '%';
        if(div.equals("rec")){
            return messageMapper.searchRecMsgCnt(userNo, searchinput, searchOp);
        } else if(div.equals("sen")){
            return messageMapper.searchSenMsgCnt(userNo, searchinput, searchOp);
        } else {
            return messageMapper.searchRecycleMsgCnt(userNo, searchinput, searchOp);
        }
    }

    public void msgInsert(MessageRequest messageRequest, int receiverNo, String receiverNm) { //insert
        messageMapper.msgInsert(messageRequest, receiverNo, receiverNm);
    }

    public void msgUpdate(int msgNo, String title, String content) { //update
        messageMapper.msgUpdate(msgNo, title, content);
    }

    public UserDto selectByUser(int userNo){ //user 정보
        return messageMapper.selectByUser(userNo);
    }

    public List<UserDto> getUsers(int userNo) { //접속한 수강생이 듣는 강의의 다른 수강생들 가져오기
        return messageMapper.getUsers(userNo);
    }

    public int getTeacher(int userNo) { //접속한 수강생이 듣는 강의의 강사
        return messageMapper.getTeacher(userNo);
    }

    public List<UserDto> classUsers(int userNo) { //강사가 접속 했을 때 강의를 듣는 학생들
        return messageMapper.classUsers(userNo);
    }

    public UserDto getAdmin(){ //관리자 정보 가져오기
        return messageMapper.getAdmin();
    }

    public List<MsgUserList> userList(){
        return messageMapper.userList(); //사용자 전체 가져오기(관리자가 사용)
    }
}
