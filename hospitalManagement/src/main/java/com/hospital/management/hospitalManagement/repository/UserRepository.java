package com.hospital.management.hospitalManagement.repository;

import com.hospital.management.hospitalManagement.entity.User;
import com.hospital.management.hospitalManagement.entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType authProviderType);

}