package com.hospital.management.hospitalManagement.Security;

import com.hospital.management.hospitalManagement.Dto.LoginRequestDto;
import com.hospital.management.hospitalManagement.Dto.LoginResponseDto;
import com.hospital.management.hospitalManagement.Dto.SignUpRequestDto;
import com.hospital.management.hospitalManagement.Dto.SignUpResponseDto;
import com.hospital.management.hospitalManagement.entity.Patient;
import com.hospital.management.hospitalManagement.entity.User;
import com.hospital.management.hospitalManagement.entity.type.AuthProviderType;
import com.hospital.management.hospitalManagement.entity.type.RoleType;
import com.hospital.management.hospitalManagement.repository.PatientRepository;
import com.hospital.management.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository repository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user =(User) authentication.getPrincipal();

        String token=authUtil.generateAccessToken(user);
        return new LoginResponseDto(token,user.getId());
    }
    public User signUpInternal(SignUpRequestDto signupRequestDto,AuthProviderType authProviderType,String providerId){
        User user =userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if(user !=null) throw new IllegalArgumentException("User Already Exists");

        user =user.builder()
               .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(signupRequestDto.getRoles())
               .build();

        if(authProviderType == AuthProviderType.Email){
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        user=userRepository.save(user);
        Patient patient= Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();
       patientRepository.save(patient);

        return user;
    }

    public SignUpResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user=signUpInternal(signupRequestDto,AuthProviderType.Email,null);
        return new SignUpResponseDto(user.getId(),user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto
            > handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

        AuthProviderType ProviderType=authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromAuth2User(oAuth2User,registrationId);

        User user=userRepository.findByProviderIdAndProviderType(providerId,ProviderType).orElse(null);

        String email=oAuth2User.getAttribute("email");

        User emailUser=userRepository.findByUsername(email).orElse(null);


        if(user==null && emailUser==null){
             String username= authUtil.DetermineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
             String name =oAuth2User.getAttribute("name");

           user=signUpInternal(new SignUpRequestDto(username,null,name,Set.of(RoleType.PATIENT)),ProviderType,providerId);
        }else if(user !=null ){
            if(email !=null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else {
            throw new BadCredentialsException("this email already registered with provider:"+emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto=new LoginResponseDto(authUtil.generateAccessToken(user),user.getId());
        return ResponseEntity.ok(loginResponseDto);


    }
}
