package com.project.lpuniv.junhyuk.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.junhyuk.dto.Comments;
import com.project.lpuniv.junhyuk.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 댓글 추가
    @PostMapping("/{postId}/comments")
    public String addComment(@PathVariable int postId, Comments comment, HttpSession session) {
        comment.setPostNo(postId); // postNo 설정
        // 로그인한 사용자의 정보를 세션에서 가져옴
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            comment.setUserNo(authInfo.getUser_no());
        }
        commentService.addComment(comment);
        return "redirect:/posts/" + postId;
    }

    // 대댓글 추가
    @PostMapping("/{postId}/comments/{commentId}/reply")
    public String addReply(@PathVariable int postId, @PathVariable int commentId, Comments comment, HttpSession session) {
        // 로그인한 사용자의 정보를 세션에서 가져옴
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            comment.setUserNo(authInfo.getUser_no());
        }else {
            return "redirect:/";
        }

        // 게시물 ID와 부모 댓글 ID 설정
        comment.setPostNo(postId);
        comment.setParentCommentNo(commentId);

        // 대댓글 추가 서비스 호출
        commentService.addReply(comment);

        // 게시물 상세보기 페이지로 리디렉션
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{commentId}/update")
    public ResponseEntity<?> updateComment(@PathVariable int commentId, @RequestBody String content, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        boolean isOwner = commentService.checkCommentOwnership(commentId, authInfo.getUser_no());
        if (!isOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 수정 권한이 없습니다.");
        }

        boolean success = commentService.updateComment(authInfo.getUser_no(), commentId, content);
        if (success) {
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정에 실패했습니다.");
        }
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        boolean isOwner = commentService.checkCommentOwnership(commentId, authInfo.getUser_no());
        if (!isOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제 권한이 없습니다.");
        }

        boolean success = commentService.deleteComment(authInfo.getUser_no(), commentId);
        if (success) {
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제에 실패했습니다.");
        }
    }








}

