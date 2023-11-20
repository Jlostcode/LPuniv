package com.project.lpuniv.minho.listenLec.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LecListDto { //강의 리스트 dto
    private int ccim_NO;
    private int occ_NO;
    private String ccim_title;
    private String ccim_content;
    private String ccim_videoID;
}
