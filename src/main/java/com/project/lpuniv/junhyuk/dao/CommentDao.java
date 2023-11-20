package com.project.lpuniv.junhyuk.dao;

import com.project.lpuniv.junhyuk.dto.Comments;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDao {
    void addComment(Comments comments);
    void addReply(Comments comments);

    List<Comments> getCommentsByPostId(int postNo);
    List<Comments> getRepliesByCommentId(int commentNo);

    void updateComment(Map<String, Object> params);
    void deleteComment(int commentNo);

    Comments findByCommentNo(int commentNo);






}
