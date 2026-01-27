package com.hospital.management.hospitalManagement.Dto;


import com.hospital.management.hospitalManagement.entity.type.bloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BloodGroupCountResponseEntity {

    private bloodGroupType bloodGroupType;
    private Long count;
}
