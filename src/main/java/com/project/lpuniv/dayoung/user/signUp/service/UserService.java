package com.project.lpuniv.dayoung.user.signUp.service;

import com.project.lpuniv.dayoung.user.signUp.dto.UserDto;
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

    public void uploadStuData(MultipartFile excelFile) {
        try (InputStream inputStream = excelFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

//                for (Row row : sheet) {
                Iterator<Row> rowIterator = sheet.iterator();

                // 첫 번째 행 건너뛰기
                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();


                    UserDto user = new UserDto();

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

                    String id = row.getCell(3).getStringCellValue();
                    String getId=row.getCell(1).getStringCellValue();
                    user.setUser_loginId(getId);
                    String password = row.getCell(4).getStringCellValue();
                    String changePassword = hashPassword(password);
                    user.setUser_passwd(changePassword);
                    System.out.println("==========================="+user);
                    userDao.insertUser(user);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String hashPassword(String password){
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
