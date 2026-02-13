package com.hospital.management.hospitalManagement.controller;

import com.hospital.management.hospitalManagement.Dto.AppointmentResponseDto;
import com.hospital.management.hospitalManagement.Dto.CreateAppointmentRequestDto;
import com.hospital.management.hospitalManagement.Dto.PatientResponseDto;
import com.hospital.management.hospitalManagement.service.AppointmentService;
import com.hospital.management.hospitalManagement.service.patientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final patientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile")
    private ResponseEntity<PatientResponseDto> getPatientProfile() {
        Long patientId = 4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }
}
