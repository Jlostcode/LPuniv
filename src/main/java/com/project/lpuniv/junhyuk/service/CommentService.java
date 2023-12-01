package com.project.lpuniv.junhyuk.service;

import com.project.lpuniv.junhyuk.dao.CommentDao;
import com.project.lpuniv.junhyuk.dto.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public void addComment(Comments comment) {
        commentDao.addComment(comment);
    }

    public void addReply(Comments comment) {

        commentDao.addReply(comment);
    }

    public List<Comments> getCommentsByPostId(int postNo) {
        List<Comments> allComments = commentDao.getCommentsByPostId(postNo);

        // 대댓글을 제거한 댓글 목록을 생성합니다.
        List<Comments> filteredComments = allComments.stream()
                .filter(c -> c.getParentCommentNo() == null)
                .collect(Collectors.toList());

        // 이제 대댓글이 아닌 댓글 목록에 대해서만 대댓글을 설정합니다.
        for (Comments comment : filteredComments) {
            List<Comments> replies = allComments.stream()
                    .filter(r -> r.getParentCommentNo() != null && r.getParentCommentNo().equals(comment.getCommentNo()))
                    .collect(Collectors.toList());
            comment.setReplies(replies);
        }

        return filteredComments; // 대댓글이 설정된 댓글 목록을 반환합니다.
    }

    public boolean isCommentOwner(int commentNo, int userNo) {
        Comments comment = commentDao.findByCommentNo(commentNo);
        System.out.println("comment : " + comment);
        return comment != null && comment.getUserNo() == userNo;
    }


    public void updateComment(String content, int commentNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("commentContent", content);
        params.put("commentNo", commentNo);
        commentDao.updateComment(params);
    }

    public void deleteComment(int commentNo) {
        commentDao.deleteComment(commentNo);
    }









}
