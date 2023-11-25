package com.project.lpuniv.minho.listenLec.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.minho.listenLec.dao.ListenLecDao;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.listenLec.dto.LecListDto;
import com.project.lpuniv.minho.listenLec.dto.LecVideoDto;
import com.project.lpuniv.minho.listenLec.dto.SchsDto;
import com.project.lpuniv.minho.listenLec.service.LecVideoService;
import com.project.lpuniv.minho.listenLec.service.LectListService;
import com.project.lpuniv.minho.listenLec.service.LecInfoService;
import com.project.lpuniv.minho.student.dto.LecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    ListenLecDao listenLecDao;

    @GetMapping("/lecInfo")
    public String getLecInfo(Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int stud_no = authInfo.getUser_no();
        List<LecDto> listenLecDtos = lecInfoService.listenLecList(stud_no);
        model.addAttribute("listenLecDtos", listenLecDtos);
        return "minho/listenLec/lecInfo";
    }

    @GetMapping("/lecList")
    public String getLecList(Model model, @RequestParam("occ_NO") int occ_NO,
                             HttpSession session) {
        List<LecListDto> lectList = lectListService.selectLecList(occ_NO);
        model.addAttribute("lectList", lectList);
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int stud_no = authInfo.getUser_no();
        int countCcimNo = listenLecDao.countCcimNo(occ_NO);
        int countSchsOcs = listenLecDao.countSchsOcs(stud_no, occ_NO);
        Double stud_pg = (double) ((100/countCcimNo) * countSchsOcs);
        lecVideoService.updateStudPg(stud_pg, stud_no, occ_NO);
        LecDto lecDto = lecVideoService.selectOneClass(stud_no, occ_NO);
        if (lecDto.getStud_pg() >= 80) {
            lecVideoService.updateStudSt(stud_no, occ_NO, stud_pg);
        }
        return "minho/listenLec/lecList";
    }

    @GetMapping("/lecVideo")
    public String getLecVideo(Model model, @RequestParam("ccim_NO") int ccim_NO,
                              @RequestParam("occ_NO") int occ_NO, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int stud_no = authInfo.getUser_no();
        LecVideoDto lecVideoDto = lecVideoService.selectLecVideo(ccim_NO, occ_NO);
        lecVideoDto.setCcim_NO(ccim_NO);
        lecVideoDto.setOcc_NO(occ_NO);
        System.out.println(lecVideoDto);
        SchsDto schsDto = lecVideoService.selectSchs(stud_no, occ_NO, ccim_NO);
        if (schsDto == null) {
            lecVideoService.insertSchs(new SchsDto(stud_no, occ_NO, ccim_NO));
            model.addAttribute("lecVideo", lecVideoDto);
            model.addAttribute("ccim_NO", ccim_NO);
            model.addAttribute("occ_NO", occ_NO);
            model.addAttribute("schsDto", schsDto);
            return "redirect:/listenLec/lecVideo";
        } else {
            model.addAttribute("lecVideo", lecVideoDto);
            model.addAttribute("ccim_NO", ccim_NO);
            model.addAttribute("occ_NO", occ_NO);
            model.addAttribute("schsDto", schsDto);
            return "minho/listenLec/lecVideo";
        }
    }

    //재생 시간 저장
    @ResponseBody
    @PostMapping(value = "/savePo", produces =  "application/json")
    public String postSaveFnpo(Model model,HttpSession session, @RequestParam("ccim_NO") int ccim_NO,
                             @RequestParam("occ_NO") int occ_NO, @RequestParam(value = "schs_fnpo") int schs_fnpo,
                             @RequestParam(value = "schs_endpo") int schs_endpo) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int stud_no = authInfo.getUser_no();
        LecVideoDto lecVideoDto = lecVideoService.selectLecVideo(ccim_NO, occ_NO);
        model.addAttribute("lecVideo", lecVideoDto);
        model.addAttribute("ccim_NO", ccim_NO);
        model.addAttribute("occ_NO", occ_NO);
        SchsDto schsDto = lecVideoService.selectSchs(stud_no, occ_NO, ccim_NO);
        System.out.println(schsDto);
        model.addAttribute("schsDto", schsDto);
        if (schsDto != null){
            lecVideoService.updatePo(stud_no, occ_NO, ccim_NO, schs_fnpo, schs_endpo);
            if (schsDto.getSchs_fnpo() >= schsDto.getSchs_endpo() - 5){
                int schs_ocs = 1;
                lecVideoService.updateOcs(stud_no, occ_NO, ccim_NO, schs_ocs);
            }
        }
        return "redirect:/listenLec/lecList?occ_NO="+occ_NO;
    }
}
