package com.project.lpuniv.heechan.message.controller;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.heechan.message.dto.Message;
import com.project.lpuniv.heechan.message.msgpage.ListMsg;
import com.project.lpuniv.heechan.message.msgpage.MsgPage;
import com.project.lpuniv.heechan.message.service.MessageService;
import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ListMsg listMsg;

    @GetMapping("/message") //모달 창
    public String test(HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        model.addAttribute("authInfo", authInfo);
        return "heechan/message/msgindex";
    }

    @GetMapping("/message/recmsg") //받은 메시지 함
    public String recMsg(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                         @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal, HttpSession session,
                         Model model){
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

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/recmsg";
    }

    @PostMapping("/message/recdel") //받은 메시지 삭제
    public String recDel(@RequestParam("msgId") int msgId, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.recDel(msgId);

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
    public String senDel(@RequestParam("msgId") int msgId, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.senDel(msgId);

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
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/recycle";
    }

    @PostMapping("/message/recycledelmsg") //휴지통 영구 삭제
    public String recycledelmsg(@RequestParam("msgId") int msgId, @RequestParam("div") String div, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Message msg = messageService.selectMsg(msgId);

        if(div.equals("sen")){
            messageService.senDel(msgId);
        } else if(div.equals("rec")){
            messageService.recDel(msgId);
        }

        if(msg.getRecDel() == 2 && msg.getSenDel() == 2){ //보낸사람, 받은사람 모두 메시지를 삭제 했을 때 DB에서도 삭제
            messageService.msgDel(msgId);
        }

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recycle";
    }

    @GetMapping("/message/msgview") //메시지 상세내용
    public String view(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "searchOp", required = false) String searchOp,
                       @RequestParam(value = "div", required = false) String div, @RequestParam(value = "pageNo", required = false) String pageNoVal,
                       @RequestParam("msgId") int msgId, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int pageNo = 1;
        if(pageNoVal != null){
            pageNo = Integer.parseInt(pageNoVal);
        }

        Message message = messageService.selectMsg(msgId);
        int userNO = authInfo.getUser_no();

        if(message.getReadFlag() == 0 && message.getReceiverId() == userNO){
            messageService.readMsg(msgId);
        }

        model.addAttribute("searchInput", searchInput);
        model.addAttribute("searchOp", searchOp);
        model.addAttribute("div", div);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("message", message);
        model.addAttribute("userNO", userNO);
        model.addAttribute("authInfo", authInfo);
        return "heechan/message/msgview";
    }

    @GetMapping("/message/writeform") //메시지 작성
    public String write(HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        UserDto user = messageService.selectByUser(authInfo.getUser_no());
        List<UserDto> users = messageService.getUsers(authInfo.getUser_no());

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "heechan/message/msgwrite";
    }

    @PostMapping("/message/recyclerecmsg") //받은 메시지 복구
    public String recycleRecMsg(@RequestParam("msgId") int msgId, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.recycleRecMsg(msgId);

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recycle";
    }

    @PostMapping("/message/recyclesenmsg") //보낸 메시지 복구
    public String recycleSenMsg(@RequestParam("msgId") int msgId, HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        messageService.recycleSenMsg(msgId);

        model.addAttribute("authInfo", authInfo);
        return "redirect:/message/recycle";
    }
}
