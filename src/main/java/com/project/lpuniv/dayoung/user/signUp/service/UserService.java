package com.project.lpuniv.dayoung.user.signUp.service;

import com.project.lpuniv.dayoung.user.signUp.dto.SignupDto;
import com.project.lpuniv.dayoung.user.signUp.dao.UserDao;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Iterator;

import static java.time.LocalTime.now;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public void uploadStuData(MultipartFile excelFile) throws IOException, NullPointerException {
        try (InputStream inputStream = excelFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            System.out.println("정보 길이===================================="+workbook.getNumberOfSheets());
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                System.out.println("i값==========================="+i);
                Sheet sheet = workbook.getSheetAt(i);
                System.out.println("i값==========================="+sheet);

//                for (Row row : sheet) {
                Iterator<Row> rowIterator = sheet.iterator();
                System.out.println(rowIterator);

                // 첫 번째 행 건너뛰기
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }

                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();


                    SignupDto user = new SignupDto();

                    if (row != null && row.getCell(0) != null && row.getCell(1) != null /* Add similar checks for other cells */) {
                        user.setUser_tp((int) row.getCell(0).getNumericCellValue());
//                    user.setUser_tp(1);
                        user.setUser_nm(row.getCell(1).getStringCellValue());
                        user.setUser_tel(row.getCell(2).getStringCellValue());
                        user.setUser_loginId(row.getCell(3).getStringCellValue());
                        user.setUser_passwd(row.getCell(4).getStringCellValue());
                        user.setUser_email(row.getCell(5).getStringCellValue());
                        user.setUser_brdt(row.getCell(6).getStringCellValue());
                        user.setUser_gen((int) row.getCell(7).getNumericCellValue());
//                    user.setUser_gen(1);
                        user.setUser_signdate(row.getCell(8).getStringCellValue());

//                        UUID uuid = UUID.randomUUID();//랜덤으로 아이디 부여하는 방법
//                        user.setUser_loginId(String.valueOf(uuid));

                        String id = row.getCell(3).getStringCellValue();
//                        String getId = row.getCell(1).getStringCellValue();//이름을 아이디로
                        String getId = row.getCell(2).getStringCellValue();
                        user.setUser_loginId(getId);
                        String password = row.getCell(4).getStringCellValue();
                        String changePassword = hashPassword(password);
                        user.setUser_passwd(changePassword);
                        user.setUser_no(0);
                        user.setUser_deletedate("hh");
                        System.out.println("===========================" + user);
                        userDao.insertUser(user);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // byte 배열을 16진수 문자열로 변환
            try (Formatter formatter = new Formatter()) {
                for (byte b : hash) {
                    formatter.format("%02x", b);
                }
                return formatter.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}