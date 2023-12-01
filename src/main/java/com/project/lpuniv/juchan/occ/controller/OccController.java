package com.project.lpuniv.juchan.occ.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.juchan.occ.dto.OccDto;
import com.project.lpuniv.juchan.occ.dto.OccDtoPage;
import com.project.lpuniv.juchan.occ.service.OccService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class OccController {

    @Autowired
    private OccService occService;
    private int size = 3;

    // 페이지당 회원 수를 기준으로 사용자 페이지 정보를 반환하는 메서드
    public OccDtoPage getOccDtoPage(int pageNum, int pageSize, int teach_NO) {
        int total = occService.occAllCount(teach_NO); // 전체 사용자 수 조회
        List<OccDto> occDtos = occService.occAllSelectDescPage((pageNum - 1) * size, size, teach_NO); // 페이지에 해당하는 사용자 목록 조회
        return new OccDtoPage(total, pageNum, size, occDtos); // 사용자 페이지 정보를 생성하여 반환
    }
    @GetMapping("occ")
    public String occMain(Model model, @RequestParam(name = "pageNo", required = false) String pageNo, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        int teach_NO = authInfo.getUser_no();
        System.out.println(authInfo);
        int pageSize = 3; // 페이지 크기 설정 (한 페이지에 보여줄 회원 수)
        int pageNum = 1;
        try {
            if (pageNo != null) {
                pageNum = Integer.parseInt(pageNo);
            }
        } catch (NumberFormatException e) {
            // 페이지 번호가 숫자로 변환되지 않을 때 처리할 코드 추가
            // 예를 들어, 기본 페이지 번호를 설정하거나 에러 핸들링을 수행합니다.
            pageNum = 1; // 기본 페이지 번호 설정
        }
//		  if (pageNo != null) {
//	            pageNum = Integer.parseInt(pageNo);
//	        }
        System.out.println(pageNo);
        OccDtoPage occDtoPage = getOccDtoPage(pageNum, pageSize, teach_NO); // 사용자 페이지 정보를 조회
        model.addAttribute("listPage", occDtoPage);
    return "/juchan/occ/occ_main";
    }

    @GetMapping("occ/occ_insert")
    public String occInsert() {
        return "/juchan/occ/occ_insert";
    }
    @PostMapping("occ/occ_insert")
    public String occInsertP(OccDto occDto, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        occDto.setTeach_NO(authInfo.getUser_no());
        occService.occInsert(occDto);
        return "redirect:/occ";
    }

    @GetMapping("occ/occ_modify")
    public String occModify(Model model, @RequestParam(value = "occ_NO")int occ_NO) {
        OccDto occDto = occService.occOneSelect(occ_NO);
        model.addAttribute("occDto", occDto);
        return "/juchan/occ/occ_modify";
    }
    @PostMapping("occ/occ_modify")
    public String occModifyPost(OccDto occDto, HttpSession session){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        occDto.setTeach_NO(authInfo.getUser_no());
        System.out.println(occDto);
        occService.occModify(occDto);

        return "redirect:/occ";
    }

    @GetMapping("/occ/occ_delete")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> occDelete(
            @RequestParam("occ_no") int occ_no, @RequestParam("occ_title") String occ_title) {
        Map<String, Object> response = new HashMap<>();

        // 삭제한 amfi_no를 응답 데이터에 추가
        response.put("occ_no", occ_no);
        response.put("occ_title", occ_title);

        // 해당 amfi_no를 삭제
        occService.occDelete(occ_no);

        System.out.println("occ_no:" + occ_no);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
