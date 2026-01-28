package com.hospital.management.hospitalManagement.repository;

import com.hospital.management.hospitalManagement.Dto.BloodGroupCountResponseEntity;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.entity.type.bloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Patient findByName(String name);
    List<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);
    List<Patient> findByNameContainingOrderByIdDesc(String query);

    @Query("select p from Patient p where p.bloodGroup = ?1")
    List<Patient> findByBloodGroup(@Param("bloodGroup") bloodGroupType bloodGroup);

    @Query("SELECT p FROM Patient p WHERE p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate );

    @Query("select new com.hospital.management.hospitalManagement.Dto.BloodGroupCountResponseEntity( p.bloodGroup,Count(p)) from Patient p group by p.bloodGroup")
   // List<Object[]> countEachBloodGrouptype();
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();

    @Query(value = "select * from patient",nativeQuery = true)
    Page<Patient> findAllPatient(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Patient p set p.name=:name where p.id=:id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);

}
