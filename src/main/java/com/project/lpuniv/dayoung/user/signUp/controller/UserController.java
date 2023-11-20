package com.project.lpuniv.dayoung.user.signUp.controller;

import com.project.lpuniv.dayoung.user.signUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService UserService;

//    @PostMapping("/excel")
//    public String uploadStudent(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            // 파일이 비어있는 경우 예외 처리
//            return "redirect:/error";
//        }
//
//        UserService.uploadStuData(file);
//
//        return "redirect:/success";
//    }
    @GetMapping("/dayoung/addStudent")
    public String addStudent() {
        return "dayoung/addStudent";
    }

    @PostMapping("/dayoung/addStudent")
    public String addStudent2(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            // 파일이 비어있는 경우 예외 처리
//            return "redirect:/error";
//        }
        UserService.uploadStuData(file);


        return "dayoung/addStudent";
    }
@GetMapping("/dayoung/modify")
    public String modifyStudent(Model model, HttpSession session){

        return "dayoung/modifyStudent";
}

    @PostMapping("/dayoung/modify")
    public String modifyStudentSuccess(){


        return "dayoung/modifyStudent";
    }

}
