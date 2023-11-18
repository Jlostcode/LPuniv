package com.project.lpuniv.juchan.amfi.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AmfiDto {

    int amfi_no;
    int amc_no;
    String amfi_og_name;
    String amfi_name;
    String amfi_path;
    int amfi_size;
    Date amfi_date;


}
