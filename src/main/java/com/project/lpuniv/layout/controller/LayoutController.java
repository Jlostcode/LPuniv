package com.project.lpuniv.layout.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.layout.dao.LayoutDao;
import com.project.lpuniv.layout.dto.LayoutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LayoutController {
@Autowired
    LayoutDao layoutDao;
    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> profileCard(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        AuthInfo authInfo =(AuthInfo) session.getAttribute("authInfo");
        int no = authInfo.getUser_no();
        LayoutDto layoutDto = layoutDao.findNm(no);
        String user = layoutDto.getUser_nm(); // LayoutDto에서 사용자 이름을 가져옴

        response.put("session", authInfo);
        response.put("userName", user); // 사용자 이름을 response에 담아 JSON으로 반환

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
