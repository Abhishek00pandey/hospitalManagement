package com.hospital.management.hospitalManagement.Security;


import com.hospital.management.hospitalManagement.entity.User;
import com.hospital.management.hospitalManagement.entity.type.PermissionType;
import com.hospital.management.hospitalManagement.entity.type.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.naming.AuthenticationException;

import static com.hospital.management.hospitalManagement.entity.type.PermissionType.*;
import static com.hospital.management.hospitalManagement.entity.type.RoleType.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
//    private final PasswordEncoder passwordEncoder;
    private final jwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/auth/**").permitAll()
                                .requestMatchers("/public/**","/auth/**")
                                .hasAnyAuthority(APPOINTMENT_DELETE.name(),
                                        USER_MANAGE.name())
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                                .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(),ADMIN.name())
                                .anyRequest().authenticated()
//                        .requestMatchers("/doctor/**").hasAnyRole("DOCTOR","ADMIN")

                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2 -> oAuth2
                        .failureHandler(
                        (request, response, exception) -> {
                            log.error("OAuth2 error: {}", exception.getMessage());
                            handlerExceptionResolver.resolveException(request,response,null,exception);
                        })
                        .successHandler(oAuth2SuccessHandler)

                )
                .exceptionHandling(exceptionHandlingConfigurer ->
                    exceptionHandlingConfigurer.accessDeniedHandler((request, response,accessDeniedException) -> {
                        handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
                    }));
               // .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
