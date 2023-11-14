package com.project.lpuniv.dayoung.user.signUp.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.signUp.dao.UserDao;
import com.project.lpuniv.dayoung.user.signUp.dto.UserDto;
import com.project.lpuniv.dayoung.user.signUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/dayoung")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserDao userDao;
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

//    @PostMapping("/dayoung/addStudent")
//    public String addStudent2(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            // 파일이 비어있는 경우 예외 처리
//            return "redirect:/error";
//        }
//        userService.uploadStuData(file);
//        System.out.println("엑셀파일"+file);
//        return "redirect:/dayoung/addStudent";
////        return "dayoung/addStudent";
//    }

    @PostMapping("/dayoung/addStudent")
    public String addStudent2(@RequestParam("file") MultipartFile file) {
        System.out.println("file이름======================================"+file);
//        if (file.isEmpty()) {
//            // 파일이 비어있는 경우 예외 처리
//            return "redirect:/error";
//        }
        userService.uploadStuData(file);


        return "dayoung/addStudent";
    }
    @GetMapping("/dayoung/addUser")
    public String addUser() {
        return "dayoung/addUser";
    }

    @PostMapping("/dayoung/addUser")
    public String addUser2(@ModelAttribute UserDto userDto ) {
        System.out.println("===="+userDto);
        String passwd= userDto.getUser_passwd();
        String hashPassword = userService.hashPassword(passwd);
        userDto.setUser_passwd(hashPassword);

        userDao.insertUser(userDto);

        System.out.println("===============================================>"+userDto);
        return "redirect:/dayoung/adminMain";

    }
    @GetMapping("/dayoung/adminMain")
    public String adminMain() {
        return "dayoung/adminMain";
    }


    @GetMapping("/dayoung/modify")
    public String modify(Model model){

//      UserDto list=  userDao.selectUser(selectUserByTel);
//        model.addAttribute("list",list);
        return "dayoung/modifyStudent";
    }
@GetMapping("/dayoung/modify/{user_tel}")
    public String modifyStudent(Model model,@RequestParam String user_tel){

    UserDto list=  userDao.selectUserByTel(user_tel);
        model.addAttribute("list",list);
        return "dayoung/modifyStudent";
}

    @PostMapping("/dayoung/modify")
    public String modifyStudentSuccess(){


        return "dayoung/modifyStudent";
    }
    @GetMapping("/dayoung/userInfo")
    public String userInfo(HttpSession session , Model model){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        System.out.println("세션값"+authInfo);
        int no= authInfo.getUser_no();
        UserDto user = userDao.selectUser(no);
        model.addAttribute("user" , user);
        return "dayoung/userInfo";
    }




}
