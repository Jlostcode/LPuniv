package com.project.lpuniv.heechan.message.controller;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.heechan.message.dto.Message;
import com.project.lpuniv.heechan.message.dto.MessageRequest;
import com.project.lpuniv.heechan.message.dto.MsgUserList;
import com.project.lpuniv.heechan.message.dto.Receiver;
import com.project.lpuniv.heechan.message.msgpage.ListMsg;
import com.project.lpuniv.heechan.message.msgpage.MsgPage;
import com.project.lpuniv.heechan.message.service.MessageService;
import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ListMsg listMsg;

    @GetMapping("/message/recmsg") //받은 메시지 함
    public String recMsg(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                         @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session,
                         Model model, @RequestParam(value = "msgCnt", required = false) Integer msgCntVal){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        int userNo = authInfo.getUser_no();

        if(searchInput != null && searchOp != null && div != null){
            MsgPage msgPage = listMsg.getSearchRecMsgPage(userNo, pageNo, searchInput, searchOp, div);
            int msgCount = messageService.searchMsgCnt(userNo, searchInput, searchOp, div);

            model.addAttribute("msgPage", msgPage);
            model.addAttribute("msgCount", msgCount);
        } else {
            MsgPage msgPage = listMsg.getRecMsgPage(userNo, pageNo);
            int msgCount = messageService.msgRecCnt(userNo);

            model.addAttribute("msgPage", msgPage);
            model.addAttribute("msgCount", msgCount);
        }

        int msgCnt = messageService.userRecMsgCnt(userNo);

        if(msgCntVal == null){
            model.addAttribute("msgCnt", msgCnt);
        } else if (msgCntVal > msgCnt) {
            model.addAttribute("msgCnt", msgCntVal);
        } else {
            model.addAttribute("msgCnt", msgCnt);
        }

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/recmsg";
    }

    @PostMapping("/message/recdel") //받은 메시지 삭제
    public String recDel(@RequestParam("msgNo") int msgNo, @RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                         @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        messageService.recDel(msgNo);

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recmsg";
    }

    @GetMapping("/message/senmsg") //보낸 메시지 함
    public String senMsg(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                         @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session,
                         Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        int userNo = authInfo.getUser_no();
        if(searchInput != null && searchOp != null && div != null){
            MsgPage msgPage = listMsg.getSearchSenMsgPage(userNo, pageNo, searchInput, searchOp, div);
            int msgCount = messageService.searchMsgCnt(userNo, searchInput, searchOp, div);

            model.addAttribute("msgPage", msgPage);
            model.addAttribute("msgCount", msgCount);
        } else{
            MsgPage msgPage = listMsg.getSenMsgPage(userNo, pageNo);
            int msgCount = messageService.msgSenCnt(userNo);

            model.addAttribute("msgPage", msgPage);
            model.addAttribute("msgCount", msgCount);
        }

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/senmsg";
    }

    @PostMapping("/message/sendel") //보낸 메시지 삭제
    public String senDel(@RequestParam("msgNo") int msgNo, @RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                         @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        messageService.senDel(msgNo);

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/senmsg";
    }

    @GetMapping("/message/recycle") //휴지통
    public String recycle(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                          @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session,
                          Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        int userNo = authInfo.getUser_no();
        if(searchInput != null && searchOp != null && div != null){
            MsgPage msgPage = listMsg.getSearchRecycleMsgPage(userNo, pageNo, searchInput, searchOp, div);
            int msgCount = messageService.searchMsgCnt(userNo, searchInput, searchOp, div);

            model.addAttribute("msgPage", msgPage);
            model.addAttribute("msgCount", msgCount);
        } else {
            MsgPage msgPage = listMsg.getRecycleMsgPage(userNo, pageNo);
            int msgCount = messageService.recycleMsgCnt(userNo);

            model.addAttribute("msgPage", msgPage);
            model.addAttribute("msgCount", msgCount);
        }

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("userNo", userNo);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/recycle";
    }

    @PostMapping("/message/recycledelmsg") //휴지통 영구 삭제
    public String recycledelmsg(@RequestParam("msgNo") int msgNo, @RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                                @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        Message msg = messageService.selectMsg(msgNo);

        if(div.equals("sen")){
            messageService.senDel(msgNo);
        } else if(div.equals("rec")){
            messageService.recDel(msgNo);
        }

        Message dleMsg = messageService.selectMsg(msgNo);

        if(dleMsg.getRecDel() == 2 && dleMsg.getSenDel() == 2){ //보낸사람, 받은사람 모두 메시지를 삭제 했을 때 DB에서도 삭제
            messageService.msgDel(msgNo);
        }

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recycle";
    }

    @GetMapping("/message/msgview") //메시지 상세내용
    public String view(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                       @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal,
                       @RequestParam("msgNo") int msgNo, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        Message message = messageService.selectMsg(msgNo);
        int userNo = authInfo.getUser_no();

        if(message.getReadFlag() == 0 && message.getReceiverNo() == userNo){
            messageService.readMsg(msgNo);
        }

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("message", message);
        model.addAttribute("userNo", userNo);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/msgview";
    }

    @GetMapping("/message/writeform") //메시지 작성
    public String writeForm(HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int userNo = authInfo.getUser_no();
        UserDto user = messageService.selectByUser(userNo);
        int userTp = user.getUser_tp();
        UserDto admin = messageService.getAdmin();

        if(userTp == 1){
            List<UserDto> users = messageService.getUsers(userNo);
            UserDto teacher = messageService.selectByUser(messageService.getTeacher(userNo));

            model.addAttribute("users", users);
            model.addAttribute("teacher", teacher);
            model.addAttribute("admin", admin);
        } else if(userTp == 2){
            List<UserDto> users = messageService.classUsers(userNo);

            model.addAttribute("users", users);
            model.addAttribute("admin", admin);
        } else {
            List<MsgUserList> msgUsersList = messageService.userList();
            model.addAttribute("msgUsersList", msgUsersList);
        }

        model.addAttribute("user", user);
        model.addAttribute("userTp", userTp);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/msgwrite";
    }

    @ResponseBody
    @PostMapping("/message/msgwrite") //메시지 작성
    public String write(@RequestBody MessageRequest messageRequest, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        for (Receiver receiver : messageRequest.getReceivers()) {
            int receiverNo = receiver.getReceiverNo();
            String receiverNm = receiver.getReceiverNm();
            messageService.msgInsert(messageRequest, receiverNo, receiverNm);
        }

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/senmsg";
    }

    @GetMapping("/message/msgupdateform") //메시지 업데이트
    public String updateForm(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                             @RequestParam(value = "div", required = false) String div, @RequestParam("msgNo") int msgNo,
                             @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        int userNo = authInfo.getUser_no();
        Message message = messageService.selectMsg(msgNo);

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("message", message);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("userNo", userNo);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/msgupdate";
    }

    @PostMapping("/message/msgupdate") //메시지 업데이트
    public String update(@RequestParam(value = "msgNo") int msgNo, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content,
                         HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.msgUpdate(msgNo, title, content);

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/senmsg";
    }

    @PostMapping("/message/recyclerecmsg") //받은 메시지 복구
    public String recycleRecMsg(@RequestParam("msgNo") int msgNo, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.recycleRecMsg(msgNo);

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recycle";
    }

    @PostMapping("/message/recyclesenmsg") //보낸 메시지 복구
    public String recycleSenMsg(@RequestParam("msgNo") int msgNo, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.recycleSenMsg(msgNo);

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recycle";
    }

    @GetMapping("/message/msgreplyform") //받은 메시지 답변 메시지 보내기
    public String msgReplyForm(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                               @RequestParam(value = "div", required = false) String div, @RequestParam("msgNo") int msgNo,
                               @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }
        int userNo = authInfo.getUser_no();
        Message msg = messageService.selectMsg(msgNo);

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("userNo", userNo);
        model.addAttribute("msg", msg);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/msgreply";
    }

    @ResponseBody
    @PostMapping("/message/msgreply") //메시지 작성
    public String msgReply(@RequestBody MessageRequest messageRequest, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        for (Receiver receiver : messageRequest.getReceivers()) {
            int receiverNo = receiver.getReceiverNo();
            String receiverNm = receiver.getReceiverNm();
            messageService.msgInsert(messageRequest, receiverNo, receiverNm);
        }

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/senmsg";
    }
}
