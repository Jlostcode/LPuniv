package com.project.lpuniv.minho.student.controller;

import com.project.lpuniv.minho.student.dto.LecDto;
import com.project.lpuniv.minho.student.dto.LecPage;
import com.project.lpuniv.minho.student.service.LectureService;
import com.project.lpuniv.minho.studentLec.service.StudentLecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
public class LecController {
    @Autowired
    LectureService lectureService;

    private int size = 5;

    public LecPage getLecPage(int pageNum, int pageSize, int occ_NO) {
        int total = lectureService.countLecPage(occ_NO);
        List<LecDto> lecDto = lectureService.selectAllLecPage((pageNum - 1) * size, size, occ_NO);
        return new LecPage(total, pageNum, size, lecDto);
    }

    @GetMapping("/stuList")
    public String getStuList(Model model, @RequestParam(name = "occ_NO") int occ_NO, @RequestParam(name = "pageNo", required = false) String pageNo, HttpSession session){
        int pageSize = 5;
        int pageNum = 1;
        if (pageNo != null) {
            pageNum = Integer.parseInt(pageNo);
        }
        LecPage lecPage = getLecPage(pageNum, pageSize, occ_NO);
        System.out.println(lecPage);
        model.addAttribute("lecPage", lecPage);
        model.addAttribute("occ_NO", occ_NO);
        return "minho/student/stuList";
    }

}
