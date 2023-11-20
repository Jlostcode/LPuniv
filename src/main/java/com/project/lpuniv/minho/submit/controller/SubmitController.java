package com.project.lpuniv.minho.submit.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.submit.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/submit")
public class SubmitController {
    @Autowired
    SubmitService submitService;

    @GetMapping("/submitForm")
    public String getSubmitForm(HttpSession session, Model model,
                                @RequestParam(value = "amc_no") int amc_no){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int user_no = authInfo.getUser_no();
        UserDto userDto = submitService.selecStunm(user_no);
        AmcDtoMH amcDtoMH = submitService.selectOneAmc(amc_no);
        model.addAttribute("userDto", userDto);
        model.addAttribute("amcDtoMH", amcDtoMH);
        System.out.println("amcDtoMH============="+amcDtoMH);
        return "minho/submit/submitForm";
    }
}
