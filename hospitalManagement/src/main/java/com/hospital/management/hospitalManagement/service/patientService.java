package com.hospital.management.hospitalManagement.service;


import com.hospital.management.hospitalManagement.Dto.PatientResponseDto;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class patientService {

        private final PatientRepository patientRepository;
        private final ModelMapper modelMapper;

        @Transactional
        public PatientResponseDto getPatientById(Long id) {
            Patient patient = patientRepository.findById(id)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Patient Not Found With Id: " + id)
                    );

            return modelMapper.map(patient, PatientResponseDto.class);
        }

        public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
            return patientRepository.findAllPatient(PageRequest.of(pageNumber, pageSize))
                    .stream()
                    .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                    .collect(Collectors.toList());
        }
    }


