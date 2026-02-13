package com.hospital.management.hospitalManagement.service;


import com.hospital.management.hospitalManagement.Dto.AppointmentResponseDto;
import com.hospital.management.hospitalManagement.Dto.CreateAppointmentRequestDto;
import com.hospital.management.hospitalManagement.entity.Appointment;
import com.hospital.management.hospitalManagement.entity.Doctor;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.repository.AppointmentRepository;
import com.hospital.management.hospitalManagement.repository.DoctorRepository;
import com.hospital.management.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    // ✅ THIS IS THE METHOD YOUR CONTROLLER SHOULD CALL
    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto dto) {

        Long doctorId = dto.getDoctorId();
        Long patientId = dto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

        Appointment appointment = Appointment.builder()
                .reason(dto.getReason())
                .appointmentTime(dto.getAppointmentTime())
                .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        // maintain bidirectional mapping
        patient.getAppointment().add(appointment);
        doctor.getAppointments().add(appointment);

        Appointment saved = appointmentRepository.save(appointment);

        return modelMapper.map(saved, AppointmentResponseDto.class);
    }

    // ---------------------------------------------

    @Transactional
    public Appointment reAssignAppointment(Long appointmentId, Long doctorId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow();

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow();

        appointment.setDoctor(doctor);
        doctor.getAppointments().add(appointment);

        return appointment;
    }

    // ---------------------------------------------

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(a -> modelMapper.map(a, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
