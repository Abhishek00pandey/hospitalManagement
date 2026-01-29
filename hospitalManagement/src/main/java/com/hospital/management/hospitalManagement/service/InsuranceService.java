package com.hospital.management.hospitalManagement.service;

import com.hospital.management.hospitalManagement.entity.Insurance;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.repository.InsuranceRepository;
import com.hospital.management.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;


    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance,Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id" + patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient);// bidirectional consistency maintained


        return patient;
    }

    @Transactional
    public Patient removalOfPatientFromInsurance(Long patientId) {
        Patient patient=patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id" + patientId));

        patient.setInsurance(null);
        return patient;
    }
}
