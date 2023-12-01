package com.project.lpuniv.dayoung.user.login.controller;


import com.project.lpuniv.dayoung.user.login.dao.LoginDao;
import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.dayoung.user.login.service.AuthService;
import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;
import com.project.lpuniv.heechan.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private MessageService messageService;
    
    @GetMapping(value = "/login")
    public String getLogin() {
        return "dayoung/loginForm";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String PostLogin(@RequestParam("user_loginId") String user_loginId, @RequestParam("user_passwd") String user_passwd, HttpSession session) {
        AuthInfo authInfo = authService.authenticate(user_loginId, user_passwd);
        session.setAttribute("authInfo", authInfo);

        String id = user_loginId;

        UserDto userDto = loginDao.loginById(id);

         String userId = userDto.getUser_loginId();

        UserDto deldate =loginDao.selectDeldate(userId);
        System.out.println("Deleted date: "+deldate);


        if(userId != null && deldate == null) {
            String hashedPasswd = hashPassword(user_passwd);

            userDto = loginDao.loginByPw(id);
            String dbPassword = userDto.getUser_passwd();


            if(hashedPasswd.equals(dbPassword)){

              int type =authInfo.getUser_tp();

                if(authInfo !=null) {
                    int user_tp =authInfo.getUser_tp();

                    if (user_tp == 1) {
                        return "redirect:/listenLec/lecInfo";
                    } else if (user_tp == 2) {
                        return "redirect:/occ";

                    } else if (user_tp == 3) {
                        return "/dayoung/adminMain";

                    }
                }else if(hashedPasswd != dbPassword){
                    return "redirect:/login";
          }

            }
        } else if (userId != null && deldate != null) {
            return "redirect:/login";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Main(@RequestParam(value = "msgCnt", required = false) Integer msgCntVal, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int userNo = authInfo.getUser_no();
        System.out.println(userNo);
        int msgCnt = messageService.userRecMsgCnt(userNo);
        System.out.println(msgCnt);
        if(msgCntVal == null){
            model.addAttribute("msgCnt", msgCnt);
        } else if (msgCntVal > msgCnt) {
            model.addAttribute("msgCnt", msgCntVal);
        } else {
            model.addAttribute("msgCnt", msgCnt);
        }

        int type = authInfo.getUser_tp();

        if (authInfo != null) {
            int user_tp = authInfo.getUser_tp();

            if (user_tp == 1) {
                return "redirect:/listenLec/lecInfo";
            } else if (user_tp == 2) {
                return "redirect:/occ";

            } else if (user_tp == 3) {
                return "/dayoung/adminMain";

            }
        }
        return "redirect:/main";
    }



    private static String hashPassword(@RequestParam("user_passwd") String user_passwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(user_passwd.getBytes());

            // 바이트를 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/login";
    }

}
