package com.project.lpuniv.minho.studentLec.controller;

import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.minho.listenLec.dto.LecInfoDto;
import com.project.lpuniv.minho.studentLec.dto.StudentLecDto;
import com.project.lpuniv.minho.studentLec.service.StudentLecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stuLec")
public class StudentLecController {
    @Autowired
    StudentLecService studentLecService;

    @GetMapping("/stuList")
    public String getstuList(Model model) {
        List<UserDto> userDtos = studentLecService.stuList();
        System.out.println("userDtos:" + userDtos);
        List<LecInfoDto> lecDtos = studentLecService.lecList();
        model.addAttribute("userDtos", userDtos);
        model.addAttribute("lecDtos", lecDtos);
        return "minho/studentLec/selectStu";
    }

    @PostMapping("/stuList")
    public String uploadStu(@RequestParam(value = "stud_no[]") List<Integer> stud_no,
                            @RequestParam(value = "occ_NO[]") List<Integer> occ_NO) {
        System.out.println("stud_no : " + stud_no);
        System.out.println("occ_NO : " + occ_NO);
        for (Integer stu : stud_no) {
            for (Integer integer : occ_NO) {
                StudentLecDto studentLecDto = studentLecService.selectClass(stu, integer);
                System.out.println("studentLecDto========================"+studentLecDto);
                if (studentLecDto == null) {
                    studentLecService.insertClass(stu, integer);
                } else {
                    stud_no = null;
                    occ_NO = null;
                    return "null";
                }
            }
        }
        return "redirect:/stuLec/stuList";
    }
}
