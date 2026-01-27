package com.hospital.management.hospitalManagement.entity;

import com.hospital.management.hospitalManagement.entity.type.bloodGroupType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
        name = "patient",
        uniqueConstraints = {
               // @UniqueConstraint( name="email will be unique",columnNames = "email"),
                @UniqueConstraint(name = "birthdate and name will be unique",columnNames = {"name","birth_date"})
        },
        indexes = {
                @Index(name="idx_patient_birth_date",columnList = "birth_date")
        }
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 40)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;

//   @ToString.Exclude
    private LocalDate birthDate;

    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime CreatedAt;

   @Enumerated(EnumType.STRING)
    private bloodGroupType bloodGroup;


   @OneToOne
   @JoinColumn(name = "patient_insurance_id") // inverse side
   private Insurance insurance;

   @OneToMany(mappedBy = "patient")
    private List<Appointment> appointment;

}
