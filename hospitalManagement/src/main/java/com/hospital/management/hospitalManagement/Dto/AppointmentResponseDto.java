package com.hospital.management.hospitalManagement.Dto;

import com.hospital.management.hospitalManagement.entity.type.bloodGroupType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private bloodGroupType bloodGroup;
}
