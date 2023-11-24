package com.project.lpuniv.junhyuk.controller;

import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.junhyuk.dto.Comments;
import com.project.lpuniv.junhyuk.service.CommentService;
import com.project.lpuniv.junhyuk.service.PostService;
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
    @ResponseBody
    public ResponseEntity<?> addComment(@PathVariable int postId, @RequestBody Comments comment, HttpSession session) {

        comment.setPostNo(postId);

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            comment.setUserNo(authInfo.getUser_no());
            comment.setPostNo(postId);
            commentService.addComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }
    }





    // 대댓글 추가
    @PostMapping("/{postId}/comments/{commentId}/reply")
    @ResponseBody
    public ResponseEntity<?> addReply(@PathVariable int postId, @PathVariable int commentId, @RequestBody Comments comment, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            comment.setUserNo(authInfo.getUser_no());
            comment.setPostNo(postId);
            comment.setParentCommentNo(commentId);
            commentService.addReply(comment);
            return ResponseEntity.ok("대댓글이 성공적으로 추가되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }
    }


    // 댓글 수정
    @PostMapping("/{commentId}/update")
    public ResponseEntity<?> updateComment(@PathVariable int commentId, @RequestBody Comments comments, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        System.out.println("authInfo==============" + authInfo);
        System.out.println("commentId : " + commentId);
        if (authInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        if (!commentService.isCommentOwner(commentId, authInfo.getUser_no())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 수정 권한이 없습니다.");
        }

        commentService.updateComment(comments.getCommentContent(), commentId);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    // 댓글 삭제
    @PostMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        if (!commentService.isCommentOwner(commentId, authInfo.getUser_no())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제 권한이 없습니다.");
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }

    // 대댓글 수정
    @PostMapping("/replies/{replyId}/update")
    public ResponseEntity<?> updateReply(@PathVariable int replyId, @RequestBody Comments reply, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        if (!commentService.isCommentOwner(replyId, authInfo.getUser_no())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("대댓글 수정 권한이 없습니다.");
        }


        commentService.updateComment(reply.getCommentContent(), replyId);
        return ResponseEntity.ok("대댓글이 성공적으로 수정되었습니다.");
    }



    // 대댓글 삭제
    @PostMapping("/replies/{replyId}/delete")
    public ResponseEntity<?> deleteReply(@PathVariable int replyId, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
        }

        if (!commentService.isCommentOwner(replyId, authInfo.getUser_no())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("대댓글 삭제 권한이 없습니다.");
        }

        commentService.deleteComment(replyId);
        return ResponseEntity.ok("대댓글이 성공적으로 삭제되었습니다.");
    }









}

