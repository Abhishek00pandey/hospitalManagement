package com.hospital.management.hospitalManagement;

import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.repository.PatientRepository;
import com.hospital.management.hospitalManagement.service.patientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class PatientTests {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private patientService patientService;

    @Test
    public void testPatientRepository(){

        List<Patient> patientList=patientRepository.findAll();
        System.out.println(patientList);
    }
    @Test
    public void testTransactionMethods() {
        //Patient patient = patientService.getPatientById(1L);
        // Patient patient=patientRepository.findByName("Rahul Singh");

//        List<Patient> patientList=patientRepository.findByBirthDateOrEmail(LocalDate.of(2000,8,25),
//                "sneha.iyer@gmail.com");
        //    List<Patient> patientList=patientRepository.findByNameContainingOrderByIdDesc("ha");
        //List<Patient> patientList=patientRepository.findByBloodGroup(bloodGroupType.AB_POSITIVE);
//        List<Patient> patientList=patientRepository.findByBornAfterDate(LocalDate.of(1996,1,1));
//
//        for (Patient patient : patientList) {
//            System.out.println(patient);
//        }
        Page<Patient> patientList=patientRepository.findAllPatient(PageRequest.of(0, 2, Sort.by("name")));
        for (Patient patient : patientList) {
            System.out.println(patient);
//        }
//        List<Object[]> objectList=patientRepository.countEachBloodGrouptype();
//        for(Object[] object:objectList){
//            System.out.println(object[0]+" "+object[1]);
//        }
//        int rowsUpdated=patientRepository.updateNameWithId("Amit Sharma",1L);
//        System.out.println(rowsUpdated);
////    }
//        List<BloodGroupCountResponseEntity> objectList = patientRepository.countEachBloodGroupType();
//        for (BloodGroupCountResponseEntity bloodGroupCountResponse : objectList) {
//            System.out.println(bloodGroupCountResponse);
//        }
    }


    }
}
