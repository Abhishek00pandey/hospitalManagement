package com.hospital.management.hospitalManagement.Dto;


import lombok.Data;

@Data
public class OnBoardDoctorRequestDto {
    private Long userId;
    private String Specialization;
    private String name;

}
