package com.project.lpuniv.junhyuk.dao;

import com.project.lpuniv.junhyuk.dto.FileAttachment;
import com.project.lpuniv.junhyuk.dto.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostDao {


    List<Post> getAllPostsByBoard(int board_no);

    int createPost(Post post);

    Post findByPostNo(@Param("post_no") int post_no);

    int getLastPostNumByBoard(@Param("board_no") int board_no);

    void PostHits(@Param("post_no") int post_no);

    int updatePost(Post post);


    int deletePost(@Param("post_no") int post_no, @Param("user_no") int user_no);



    List<Post> searchPosts(@Param("board_no") int board_no,
                           @Param("searchType") String searchType,
                           @Param("searchTerm") String searchTerm,
                           @Param("limit") int limit,
                           @Param("offset") int offset);

    int countSearchPosts(@Param("board_no") int board_no,
                         @Param("searchTerm") String searchTerm,
                         @Param("searchType") String searchType);

    List<Post> getAllPostsByBoardWithPaging(@Param("board_no") int board_no,
                                            @Param("limit") int limit,
                                            @Param("offset") int offset);


    int countPosts(@Param("board_no") int board_no);

    List<Post> selectPostsWithCommentCount(int board_no);

    List<Post> searchPostsWithComments(int board_no, String searchType, String searchTerm, int limit, int offset);
    List<Post> getAllPostsWithCommentsByBoardWithPaging(int board_no, int limit, int offset);

}