package com.project.lpuniv.minho.submit.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.juchan.amfi.dto.AmfiDto;
import com.project.lpuniv.minho.amc.dto.AmcDtoMH;
import com.project.lpuniv.minho.file.dto.FileDtoMH;
import com.project.lpuniv.minho.file.service.FileServiceMH;
import com.project.lpuniv.minho.submit.dto.SubmitDto;
import com.project.lpuniv.minho.submit.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        SubmitDto submitDto = submitService.selectSubmitAmcno(user_no, amc_no);
        model.addAttribute("userDto", userDto);
        model.addAttribute("amcDtoMH", amcDtoMH);
        model.addAttribute("submitDto", submitDto);
        return "minho/submit/submitForm";
    }

    @PostMapping("/send")
    public String postSubmit(@RequestParam(name = "files", required = false) List<MultipartFile> files,
                             @RequestParam("stud_no") int stud_no, @RequestParam("occ_no") int occ_no,
                             @RequestParam("amc_no") int amc_no, @RequestParam("submit_ct") String submit_ct
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
                    int submit_no = submitService.selectSubmit();
                    fileServiceMH.insertFile(file, submit_no);
                }
            }
            return "redirect:/amc/amcView?amc_no=" + amc_no;
        } catch (NullPointerException e) {
            return "errorPage";
        }
    }

    @GetMapping("/listSubmit")
    public String selectAllSubmit(@RequestParam("amc_no") int amc_no, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        AmcDtoMH amcDtoMH = submitService.selectOneAmc(amc_no);
        List<SubmitDto> submitDtos = submitService.selectAllSubmit(amc_no);
        model.addAttribute("submitDtos", submitDtos);
        model.addAttribute("amcDtoMH", amcDtoMH);
        return "minho/submit/listSubmit";
    }

    @GetMapping("/selStuSub")
    public String selectOneStuSubmit(@RequestParam("amc_no") int amc_no, @RequestParam("stud_no") int stud_no,
                                     HttpSession session, Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        SubmitDto submitDto = submitService.selectOneStuSubmit(amc_no, stud_no);
        model.addAttribute("submitDto", submitDto);
        AmcDtoMH amcDtoMH = submitService.selectOneAmc(amc_no);
        model.addAttribute("amcDtoMH", amcDtoMH);
        int submit_no = submitDto.getSubmit_no();
        List<FileDtoMH> fileDtoMHS = fileServiceMH.selectFile(submit_no);
        model.addAttribute("fileDtoMHS",fileDtoMHS);
        return "minho/submit/selStuSub";
    }

    @PostMapping("/updateScore")
    @ResponseBody
    public void updateSc(int submit_no, int submit_sc) {
        System.out.println("submit_no======================="+submit_sc);
        submitService.updateScore(submit_no, submit_sc);
    }
}
