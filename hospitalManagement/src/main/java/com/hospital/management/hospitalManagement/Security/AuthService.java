package com.hospital.management.hospitalManagement.Security;

import com.hospital.management.hospitalManagement.Dto.LoginRequestDto;
import com.hospital.management.hospitalManagement.Dto.LoginResponseDto;
import com.hospital.management.hospitalManagement.entity.User;
import com.hospital.management.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.Authenticator;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user =(User) authentication.getPrincipal();

        String token=authUtil.generateAccessToken(user);
        return new LoginResponseDto(token,user.getId());
    }
}
