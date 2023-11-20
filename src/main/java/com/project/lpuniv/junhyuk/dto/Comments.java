package com.project.lpuniv.junhyuk.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comments {

    private int commentNo;
    private int postNo;
    private String commentContent;
    private LocalDateTime commentDate;
    private int userNo;
    private String userName;
    private Integer parentCommentNo;
    private List<Comments> replies;

}
