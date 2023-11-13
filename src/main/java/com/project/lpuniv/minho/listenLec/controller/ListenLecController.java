package com.project.lpuniv.minho.listenLec.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.listenLec.dto.LecListDto;
import com.project.lpuniv.minho.listenLec.dto.LecVideoDto;
import com.project.lpuniv.minho.listenLec.dto.SchsDto;
import com.project.lpuniv.minho.listenLec.service.LecVideoService;
import com.project.lpuniv.minho.listenLec.service.LectListService;
import com.project.lpuniv.minho.listenLec.service.LecInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/listenLec")
public class ListenLecController {
    @Autowired
    LecInfoService lecInfoService;
    @Autowired
    LectListService lectListService;
    @Autowired
    LecVideoService lecVideoService;

    @GetMapping("/lecInfo")
    public String getLecInfo(Model model) {
        List<LecInfoDto> listenLecDtos = lecInfoService.selectAllLitenLec();
        model.addAttribute("dto", listenLecDtos);
        return "minho/listenLec/lecInfo";
    }

    @GetMapping("/lecList")
    public String getLecList(Model model, @RequestParam("occ_NO") int occ_NO) {
        List<LecListDto> lectList = lectListService.selectLecList(occ_NO);
        System.out.println(lectList);
        model.addAttribute("lectList", lectList);
        return "minho/listenLec/lecList";
    }

    @GetMapping("/lecVideo")
    public String getLecVideo(Model model, @RequestParam("ccim_NO") int ccim_NO,
                              @RequestParam("occ_NO") int occ_NO, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int stud_no = authInfo.getUser_no();
        System.out.println(stud_no);
        LecVideoDto lecVideoDto = lecVideoService.selectLecVideo(ccim_NO, occ_NO);
        System.out.println("```````````````ccim_no="+ccim_NO);
        System.out.println("```````````````occ_NO="+occ_NO);
        SchsDto schsDto = lecVideoService.selectSchs(stud_no);
        if (schsDto == null) {
            lecVideoService.insertSchs(schsDto);
            System.out.println("```````````````schsDto="+schsDto);
        }
        model.addAttribute("lecVideo", lecVideoDto);
        return "minho/listenLec/lecVideo";
    }
}
