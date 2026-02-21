package com.hospital.management.hospitalManagement.service;

import com.hospital.management.hospitalManagement.Dto.DoctorResponseDto;
import com.hospital.management.hospitalManagement.Dto.OnBoardDoctorRequestDto;
import com.hospital.management.hospitalManagement.entity.Doctor;
import com.hospital.management.hospitalManagement.entity.User;
import com.hospital.management.hospitalManagement.entity.type.RoleType;
import com.hospital.management.hospitalManagement.repository.DoctorRepository;
import com.hospital.management.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnBoardDoctorRequestDto onBoardDoctorRequestDto){
        User user=userRepository.findById(onBoardDoctorRequestDto.getUserId()).orElseThrow();

        if (doctorRepository.existsById(onBoardDoctorRequestDto.getUserId())){
            throw new IllegalArgumentException("Already a Doctor");
        }

        Doctor doctor= Doctor.builder()
                .name(onBoardDoctorRequestDto.getName())
                .specialisation(onBoardDoctorRequestDto.getSpecialization())
                .user(user)
                .build();

        user.getRoles().add(RoleType.DOCTOR);

        return modelMapper.map(doctorRepository.save(doctor),DoctorResponseDto.class);

    }
}
