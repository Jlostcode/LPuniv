package com.project.lpuniv.junhyuk.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Post {


    private int post_no;
    private int board_no;
    private Integer num;
    private String title;
    private String content;
    private LocalDateTime date_created;
    private Integer hits = 0;
    private int user_no;
    private String authorName;
    private int commentCount;


}
