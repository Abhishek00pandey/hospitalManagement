package com.hospital.management.hospitalManagement.service;


import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.repository.patientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class patientService {
    private final patientRepository patientRepository;

    @Transactional
    public Patient getPatientById(Long id){
        Patient p1= patientRepository.findById(id).orElseThrow() ;
        Patient p2= patientRepository.findById(id).orElseThrow() ;
        p1.setName("gear");
        return p1;
    }

}
