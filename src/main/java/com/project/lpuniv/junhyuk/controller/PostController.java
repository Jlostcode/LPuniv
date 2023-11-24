package com.project.lpuniv.junhyuk.controller;


import com.project.lpuniv.dayoung.user.login.dto.AuthInfo;
import com.project.lpuniv.dayoung.user.login.dto.UserDto;
import com.project.lpuniv.junhyuk.dto.Comments;
import com.project.lpuniv.junhyuk.dto.FileAttachment;
import com.project.lpuniv.junhyuk.dto.Post;
import com.project.lpuniv.junhyuk.service.BoardService;
import com.project.lpuniv.junhyuk.service.CommentService;
import com.project.lpuniv.junhyuk.service.FileService;
import com.project.lpuniv.junhyuk.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/boards")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    BoardService boardService;

    @Autowired
    FileService fileService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/main")
    public String mainPage() {

        return "main";
    }

    @GetMapping("/{board_name}/posts")
    public String getAllPostsByBoard(@PathVariable String board_name,
                                     @RequestParam(required = false) String searchType,
                                     @RequestParam(required = false) String searchTerm,
                                     @RequestParam(defaultValue = "1") int page,
                                     HttpSession session, Model model) {
        int board_no = boardService.getBoardNumberByName(board_name);
        int size = 10;
        List<Post> posts;
        int totalPosts;






        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null){
            model.addAttribute("currentUserId", authInfo.getUser_no());
        }


        if(page <= 1) {
            page = 1;
        }


        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            posts = postService.searchAndPaginatePostsWithComments(board_no, searchType, searchTerm.trim(), page, size);
            totalPosts = postService.countSearchPosts(board_no, searchTerm.trim(), searchType);
        } else {
            posts = postService.getAllPostsWithCommentsByBoardWithPaging(board_no, page, size);
            totalPosts = postService.countPosts(board_no);
        }

        int totalPages = (int) Math.ceil((double) totalPosts / size);




        model.addAttribute("posts", posts);
        model.addAttribute("totalPosts", totalPosts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("board_no", board_no);
        model.addAttribute("board_name", board_name);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchTerm", searchTerm);

        return "junhyuk/posts/" + board_name;
    }








    @GetMapping("/{board_name}/posts/create")
    public String showCreateForm(@PathVariable("board_name") String board_name, Model model) {
        int board_no = boardService.getBoardNumberByName(board_name);

        String listURL = "/boards/" + board_name + "/posts";
        model.addAttribute("listURL", listURL);

        model.addAttribute("board_no", board_no);
        model.addAttribute("post", new Post());
        model.addAttribute("board_name", board_name);
        return "junhyuk/posts/create";
    }



    @PostMapping("/{board_name}/posts")
    public String createPost(@PathVariable("board_name") String board_name,
                             @ModelAttribute Post post,
                             @RequestParam("files") MultipartFile[] files,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo == null) {
            return "redirect:/";
        }

        post.setUser_no(authInfo.getUser_no());


        int board_no = boardService.getBoardNumberByName(board_name);
        post.setBoard_no(board_no);
        int postNo = postService.createPost(post);

        for (MultipartFile file : files) { // 여러 파일을 처리하기 위한 반복문
            if (!file.isEmpty()) {
                try {
                    String originalFileName = file.getOriginalFilename();
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

                    String storedFileName = UUID.randomUUID().toString() + fileExtension;

                    Path path = Paths.get("C:\\upload\\" + storedFileName);
                    Files.createDirectories(path.getParent());
                    Files.copy(file.getInputStream(), path);

                    // 파일 메타데이터 저장
                    FileAttachment fileAttachment = new FileAttachment();
                    fileAttachment.setPost_no(postNo);
                    fileAttachment.setFile_name(storedFileName);
                    fileAttachment.setOriginal_file_name(originalFileName);
                    fileAttachment.setFilePath(path.toString());
                    fileAttachment.setFileSize((int) file.getSize());
                    fileAttachment.setFileUploadedDate(LocalDateTime.now());

                    fileService.save(fileAttachment);

                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "파일 업로드에 실패했습니다: " + e.getMessage());
                    return "redirect:/boards/" + board_name + "/posts/create";
                }
            }
        }

        if (files.length > 0) {
            redirectAttributes.addFlashAttribute("message", "게시글 및 파일이 성공적으로 업로드 되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 업로드 되었습니다.");
        }

        return "redirect:/boards/" + board_name + "/posts";
    }





    @GetMapping("/{board_name}/posts/{post_no}")
    public String viewPost(@PathVariable String board_name, @PathVariable("post_no") int post_no, Model model, HttpSession session) {
        // 조회수
        postService.postHits(post_no);

        Post post = postService.findByPostNo(post_no);
        System.out.println("++++++++++++++++++" + post);
        String authorName = post.getAuthorName();
        System.out.println("------------------" + authorName);
        model.addAttribute("authorName", authorName);
        List<FileAttachment> attachments = fileService.findAttachmentsByPostNo(post_no);

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");


        boolean isOwner = authInfo != null && authInfo.getUser_no() == post.getUser_no();






        model.addAttribute("postId", post_no);


        List<Comments> commentsWithReplies = commentService.getCommentsByPostId(post_no);
        System.out.println("commentsWithReplies"+commentsWithReplies);
        model.addAttribute("comments", commentsWithReplies);


        String listURL = "/boards/" + board_name + "/posts";
        model.addAttribute("listURL", listURL);

        model.addAttribute("post", post);
        model.addAttribute("attachments", attachments);
        model.addAttribute("isOwner", isOwner); // 게시물의 소유권 여부를 모델에 추가


        return "junhyuk/posts/view";
    }






    @GetMapping("/{board_name}/posts/{post_no}/edit")
    public String showEditPostForm(@PathVariable String board_name, @PathVariable int post_no, Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null || !postService.isOwner(post_no, authInfo.getUser_no())) {
            return "redirect:/login";
        }

        Post post = postService.findByPostNo(post_no);
        List<FileAttachment> attachments = fileService.findAttachmentsByPostNo(post_no); // 첨부된 파일 목록을 가져옵니다.

        model.addAttribute("post", post);
        model.addAttribute("attachments", attachments); // 파일 목록을 모델에 추가합니다.
        return "junhyuk/posts/edit";
    }

    @PostMapping("/{board_name}/posts/{post_no}/edit")
    public String updatePost(@PathVariable String board_name, @PathVariable int post_no,
                             @ModelAttribute Post post,
                             @RequestParam("newFiles") MultipartFile[] newFiles,
                             HttpSession session, RedirectAttributes redirectAttributes) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return "redirect:/login";
        }


        Post existingPost = postService.findByPostNo(post_no);
        if (existingPost == null || existingPost.getUser_no() != authInfo.getUser_no()) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
            return "redirect:/boards/" + board_name + "/posts/" + post_no;
        }

        post.setPost_no(post_no);
        post.setUser_no(authInfo.getUser_no());
        postService.updatePost(post);
        // 새로운 파일 처리 로직
        for (MultipartFile file : newFiles) {
            if (!file.isEmpty()) {
                try {
                    String originalFileName = file.getOriginalFilename();
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

                    String storedFileName = UUID.randomUUID().toString() + fileExtension;
                    Path path = Paths.get("C:\\upload\\" + storedFileName);
                    Files.createDirectories(path.getParent());
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                    // 파일 메타데이터 저장
                    FileAttachment fileAttachment = new FileAttachment();
                    fileAttachment.setPost_no(post_no);
                    fileAttachment.setFile_name(storedFileName);
                    fileAttachment.setOriginal_file_name(originalFileName);
                    fileAttachment.setFilePath(path.toString());
                    fileAttachment.setFileSize((int) file.getSize());
                    fileAttachment.setFileUploadedDate(LocalDateTime.now());

                    fileService.save(fileAttachment);
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드에 실패했습니다: " + e.getMessage());
                    return "redirect:/boards/" + board_name + "/posts/" + post_no + "/edit";
                }
            }
        }

        redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 수정되었습니다.");
        return "redirect:/boards/" + board_name + "/posts/" + post_no;
    }




    @PostMapping("/{board_name}/posts/{post_no}/delete")
    public String deletePost(@PathVariable String board_name, @PathVariable int post_no, HttpSession session, RedirectAttributes redirectAttributes) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null || !postService.isOwner(post_no, authInfo.getUser_no())) {
            return "redirect:/login";
        }

        postService.deletePost(post_no, authInfo.getUser_no());
        redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 삭제되었습니다.");
        return "redirect:/boards/" + board_name + "/posts";
    }
















}