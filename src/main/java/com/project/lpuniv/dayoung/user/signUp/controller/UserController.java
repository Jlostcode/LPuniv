package com.project.lpuniv.dayoung.user.signUp.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.signUp.dao.UserDao;
import com.project.lpuniv.dayoung.user.signUp.dto.ListDto;
import com.project.lpuniv.dayoung.user.signUp.dto.ListPage;
import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;

import com.project.lpuniv.dayoung.user.signUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/dayoung")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserDao userDao;

    @GetMapping("/dayoung/addStudent")
    public String addStudent() {


        return "dayoung/addStudent";
    }

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


    @GetMapping("/dayoung/modifyStudent")
    public String modify(Model model){

//      UserDto list=  userDao.selectUser(selectUserByTel);
//        model.addAttribute("list",list);
        return "dayoung/modifyStudent";
    }

    @GetMapping("/dayoung/modify")
    public String modifyUser(Model model){

//      UserDto list=  userDao.selectUser(selectUserByTel);
//        model.addAttribute("list",list);
        return "dayoung/modifyStudent";
    }

    @GetMapping("/dayoung/users")
    public ResponseEntity<List<ListDto>> getUsers() {
        List<ListDto> users = userDao.gridList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/dayoung/list")
    public String gridList() {
        return "dayoung/gridList";
    }



    @PostMapping("/dayoung/update")
    @ResponseBody
    public String addGridList(@RequestBody List<ListDto> updatedData) {
        System.out.println(updatedData);

        for (ListDto data : updatedData) {
            System.out.println(data);
            userDao.updateUser(data); // 예시로 userDao를 사용하여 데이터베이스 업데이트
        }

        return "success"; //
    }

//    @PostMapping("/dayoung/update")
//    @ResponseBody
//    public ResponseEntity<Map<String, String>> addGridList(@RequestBody List<ListDto> updatedData) {
//        System.out.println(updatedData);
//        Map<String, String> response = new HashMap<>();
//        for (ListDto data : updatedData) {
//            System.out.println(data);
//            userDao.updateUser(data); // 예시로 userDao를 사용하여 데이터베이스 업데이트
//        }
//
//        response.put("status", "success");
//        return ResponseEntity.ok(response); // 업데이트 후의 화면으로 리다이렉트할 수 있는 URL을 반환합니다
//    }


    @PostMapping("/dayoung/modify")
    public String modifyStudentSuccess(@RequestParam String user_tel,HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
       int no= authInfo.getUser_no();
        String tel = user_tel;

        if(tel != null){
            userDao.modifySelf(no,user_tel);
            return "redirect:/dayoung/userInfo";

        }
        return "redirect:/dayoung/modify";
    }
    @PostMapping("/dayoung/deleteDate") //유저가 탈퇴 시 deletedate 값만 찍히고 로그인 안되게 하는 기능
    public String deleteDate(HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int no = authInfo.getUser_no();
        userDao.deleteDate(no);
        return "redirect:/login";
    }

    @PostMapping("/dayoung/deleteUser")//관리자가 실제로 db상의 정보까지 삭제
    @ResponseBody
    public String deleteUser(@RequestBody List<ListDto> updatedData){
            for (ListDto data : updatedData) {
                int id = data.getUser_no();
                System.out.println(data);
                userDao.delUser(id); // 예시로 userDao를 사용하여 데이터베이스 업데이트
            }
             return"redirect:/dayoung/gridList";
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

    @GetMapping("/dayoung/checkPassword") // 유저가 개인정보 수정 시 비밀번호 인증
    @ResponseBody
    public ResponseEntity<Map<String, String>> getUserInfo(@RequestParam("currentPassword") String currentPassword, HttpSession session){
        Map<String, String> response = new HashMap<>();

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        String dbPasswd=authInfo.getUser_passwd();
        System.out.println(currentPassword);
        String hashedCurrentPassword = hashPassword(currentPassword);
        System.out.println(hashedCurrentPassword);
        if (hashedCurrentPassword.equals(dbPasswd)) {
            session.setAttribute("isPasswordVerified", true);
            response.put("status", "success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", "false");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }


    @GetMapping("/dayoung/modifyPw")
    @ResponseBody
    public ResponseEntity<Map<String, String>> changeUserPw(@RequestParam("newPassword") String newPassword,HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Map<String, String> response = new HashMap<>();
        int id = authInfo.getUser_no();
        System.out.println(newPassword);
        if (newPassword!=null){
            String hashPassword = hashPassword(newPassword);
            userDao.updateUserPw(id,hashPassword);
            response.put("status", "success");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else {
            response.put("status", "false");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }


    }



    @PostMapping("/dayoung/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody List<ListDto> updatedData){
        for (ListDto data : updatedData) {
           int id= data.getUser_no();
            System.out.println(data);
            userDao.resetPw(id); // 예시로 userDao를 사용하여 데이터베이스 업데이트
        }

        return"redirect:/dayoung/gridList";
        }

    private static String hashPassword(String currentPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(currentPassword.getBytes());

            // 바이트를 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
