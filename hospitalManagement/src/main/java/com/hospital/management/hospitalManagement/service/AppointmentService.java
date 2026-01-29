package com.hospital.management.hospitalManagement.service;


import com.hospital.management.hospitalManagement.entity.Appointment;
import com.hospital.management.hospitalManagement.entity.Doctor;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.repository.AppointmentRepository;
import com.hospital.management.hospitalManagement.repository.DoctorRepository;
import com.hospital.management.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Transactional
    public Appointment createdNewAppointment(Appointment appointment, Long patientId, Long doctorId){
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();
        Patient patient=patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId() !=null) throw new IllegalArgumentException("Appointment already exists");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointment().add(appointment); // to maintain bidirectional consistency

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment ReAssignAppointment(Long appointmentId,  Long doctorId){
        Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);// this will automatically call the update ,because it is dirty now

        doctor.getAppointments().add(appointment);// just for bidirectional consistency
        return appointment;
    }

//    @Transactional
//    public Appointment appointmentOne(Appointment appointment, Long patientId, Long doctorId){
//        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();
//        Patient patient=patientRepository.findById(patientId).orElseThrow();
//
//        if(appointment.getId() !=null) throw new IllegalArgumentException("Appointment already exists");
//        appointment.setPatient(patient);
//        appointment.setDoctor(doctor);
//        patient.getAppointment().add(appointment); // to maintain bidirectional consistency
//
//        return appointmentRepository.save(appointment);
//
//    }


}
