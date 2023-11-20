package com.project.lpuniv.minho.submit.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.file.service.FileServiceMH;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import com.project.lpuniv.minho.submit.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/submit")
public class SubmitController {
    @Autowired
    SubmitService submitService;
    @Autowired
    FileServiceMH fileServiceMH;

    @GetMapping("/submitForm")
    public String getSubmitForm(HttpSession session, Model model,
                                @RequestParam(value = "amc_no") int amc_no){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int user_no = authInfo.getUser_no();
        UserDto userDto = submitService.selecStunm(user_no);
        AmcDtoMH amcDtoMH = submitService.selectOneAmc(amc_no);
        model.addAttribute("userDto", userDto);
        model.addAttribute("amcDtoMH", amcDtoMH);
        return "minho/submit/submitForm";
    }

    @PostMapping("/submitForm")
    public String postSubmit(@RequestParam(name = "files", required = false) List<MultipartFile> files,
                             @RequestParam("stud_no") int stud_no, @RequestParam("occ_no") int occ_no,
                             @RequestParam("amc_no") int amc_no, @RequestParam("submit_no") int submit_no,
                             @RequestParam("submit_ct") String submit_ct
    ) throws IOException {
        try {
            if (files == null) {
                files = Collections.emptyList();//파일이 전송되지 않은 경우 빈 리스트로 초기화
            }
            SubmitDto submitDto = new SubmitDto();
            submitDto.setStud_no(stud_no);
            submitDto.setOcc_NO(occ_no);
            submitDto.setAmc_no(amc_no);
            submitDto.setSubmit_ct(submit_ct);
            submitService.insertSubmit(submitDto);

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    fileServiceMH.insertFile(submit_no, file);
                }
            }
            return "redirect:/amc/amcView";
        } catch (NullPointerException e) {
            return "errorPage";
        }
    }
}
