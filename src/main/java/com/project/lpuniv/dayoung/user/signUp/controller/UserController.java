package com.project.lpuniv.dayoung.user.signUp.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.signUp.dao.UserDao;
import com.project.lpuniv.dayoung.user.signUp.dto.ListDto;
import com.project.lpuniv.dayoung.user.signUp.dto.ListPage;
import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;

import com.project.lpuniv.dayoung.user.signUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
//@RequestMapping("/dayoung")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserDao userDao;

    private int size = 10;
    public ListPage getUserPage(int pageNum, int pageSize) {
        int total = userDao.countUser();
        System.out.println("user의 총 합"+total);
        List<ListDto> content = userDao.userList((pageNum-1)*size,size);
        return new ListPage(total,pageNum,size,content);
    }
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
    public String addStudent2(@RequestParam("file") MultipartFile file) throws IOException {
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
    public String addUser2(@ModelAttribute SignupDto signupDto) {
        System.out.println("===="+ signupDto);
        String passwd= signupDto.getUser_passwd();
        String hashPassword = userService.hashPassword(passwd);
        signupDto.setUser_passwd(hashPassword);

        userDao.insertUser(signupDto);

        System.out.println("===============================================>"+ signupDto);
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


//    @GetMapping("/dayoung/list")
//    public String userList(Model model, @RequestParam(name="pageNo", required = false)String pageNo,HttpSession session,
//                           @RequestParam(name="selectOption", required = false)String selectOption,@RequestParam(name="serchFind", required = false)String serchFind){
//            AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
//
//            int pageSize = 4; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
//            int pageNum = 1;
//
//            if (pageNo != null) {
//                pageNum = Integer.parseInt(pageNo);
//            }
//
//            if(selectOption!=null && serchFind != null){
//                String find = '%'+serchFind+'%';
////                List<ListDto> searchUser = userDao.serchList(find,selectOption,pageNum-1,pageSize);
//                model.addAttribute("find",find);
//                model.addAttribute("selectOption",selectOption);
//                model.addAttribute("pageNum", pageNum);
//                model.addAttribute("pageSize", pageSize);
//
//
//            }else {
////            List<ListDto> search = userDao.userList(pageNum,pageSize);
//                ListPage listPage = getUserPage(pageNum, pageSize);
//                model.addAttribute("userPage", listPage);
//                model.addAttribute("pageNo", pageNo);
//
//
//            }
//        return "dayoung/userList";
//        }


    @GetMapping("/dayoung/users")
    public ResponseEntity<List<ListDto>> getUsers() {
        List<ListDto> users = userDao.gridList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/dayoung/list")
    public String gridList() {
        return "dayoung/gridList";
    }


    @PostMapping("/dayoung/list")
    public String addGridList(@RequestParam List<ListDto> updatedData) {


        for (ListDto data : updatedData) {
            userDao.updateUser(data); // 예시로 userDao를 사용하여 데이터베이스 업데이트
        }

        return "업데이트가 완료되었습니다."; // 업데이트 후의 화면으로 리다이렉트할 수 있는 URL을 반환합니다.
    }



    @GetMapping("/dayoung/modify/{user_tel}")
    public String modifyStudent(Model model,@RequestParam String user_tel){

    SignupDto list=  userDao.selectUserByTel(user_tel);
        System.out.println(list);
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
        SignupDto user = userDao.selectUser(no);
        model.addAttribute("user" , user);
        return "dayoung/userInfo";
    }




}
