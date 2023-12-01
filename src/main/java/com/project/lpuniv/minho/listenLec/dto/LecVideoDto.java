package com.project.lpuniv.minho.listenLec.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LecVideoDto { //강의 영상 dto
    private int ccim_NO;
    private int occ_NO;
    private String ccim_videoID;
}
