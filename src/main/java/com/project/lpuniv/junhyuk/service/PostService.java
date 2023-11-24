package com.project.lpuniv.junhyuk.service;

import com.project.lpuniv.junhyuk.dao.PostDao;
import com.project.lpuniv.junhyuk.dto.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PostService {

    @Autowired
    private PostDao postDao;

    public List<Post> getAllPostsByBoard(int board_no) {
        return postDao.getAllPostsByBoard(board_no);
    }

    public int createPost(Post post) {
        int lastNum = postDao.getLastPostNumByBoard(post.getBoard_no());
        post.setNum(lastNum + 1);
        postDao.createPost(post);
        return post.getPost_no();
    }



    public int getLastPostNumByBoard(int board_no) {

        return postDao.getLastPostNumByBoard(board_no);
    }

    public Post findByPostNo(int post_no) {

        return postDao.findByPostNo(post_no);
    }

    public boolean updatePost(Post post) {
        return postDao.updatePost(post) > 0;
    }


    public boolean deletePost(int postNo, int userNo) {
        return postDao.deletePost(postNo, userNo) > 0;
    }

    public boolean isOwner(int postNo, int userNo) {
        Post post = postDao.findByPostNo(postNo);
        return post != null && post.getUser_no() == userNo;
    }

    public List<Post> searchAndPaginatePosts(int board_no, String searchType, String searchTerm, int page, int size) {
        int limit = size;
        int offset = (page - 1) * size;
        return postDao.searchPosts(board_no, searchType, searchTerm, limit, offset);
    }

    public int countSearchPosts(int board_no, String searchTerm, String searchType) {
        return postDao.countSearchPosts(board_no, searchTerm, searchType);
    }


    public List<Post> getAllPostsByBoardWithPaging(int board_no, int page, int size) {
        int offset = (page - 1) * size;
        return postDao.getAllPostsByBoardWithPaging(board_no, size, offset);
    }


    public int countPosts(int board_no) {
        return postDao.countPosts(board_no);
    }

    public void postHits(int post_no){
        postDao.PostHits(post_no);
    }

    public List<Post> getPostsWithCommentCount(int boardNo) {
        return postDao.selectPostsWithCommentCount(boardNo);
    }


    public List<Post> searchAndPaginatePostsWithComments(int board_no, String searchType, String searchTerm, int page, int size) {
        int limit = size;
        int offset = (page - 1) * size;
        return postDao.searchPostsWithComments(board_no, searchType, searchTerm, limit, offset);
    }

    public List<Post> getAllPostsWithCommentsByBoardWithPaging(int board_no, int page, int size) {
        int offset = (page - 1) * size;
        return postDao.getAllPostsWithCommentsByBoardWithPaging(board_no, size, offset);
    }


}