package com.project.lpuniv.minho.file.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileDtoMH {
    private int file_no;
    private int submit_no;
    private String file_uuid;//파일명
    private String file_path;//파일 경로
    private String file_nm;//실제 파일명
    private int file_cp;//파일 크기
}
