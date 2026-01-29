package com.hospital.management.hospitalManagement;

import com.hospital.management.hospitalManagement.entity.Appointment;
import com.hospital.management.hospitalManagement.entity.Insurance;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.service.AppointmentService;
import com.hospital.management.hospitalManagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private AppointmentService appointmentService;
    @Test
    public void testInsurance() {
        Insurance insurance =Insurance.builder()
                .policyNumber("axix_1234")
                .provider("axis")
                .validUntil(LocalDate.of(2030,12,12))
                .build();
       Patient patient= insuranceService. assignInsuranceToPatient(insurance,1L);
        System.out.println(patient);

       var newPatient= insuranceService.removalOfPatientFromInsurance(patient.getId());
        System.out.println(newPatient);
    }
    @Test
    public void testAppointment() {
        Appointment appointment=Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026,2,15,10,0))
                .reason("fracture")
                .build();
        var newappointment= appointmentService.createdNewAppointment(appointment,2L,1L);
        System.out.println(newappointment);

       var updatedAppointment= appointmentService.ReAssignAppointment(newappointment.getId(),3L);
        System.out.println(updatedAppointment);

    }

    @Test
    public void testNewAppointment() {
        Appointment appointment=Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026,1,30,10,0))
                .reason("minor fever")
                .build();
       var firstAppointment= appointmentService.createdNewAppointment(appointment,5L,3L);
        System.out.println(firstAppointment);
    }
}
