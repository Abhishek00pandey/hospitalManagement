package com.hospital.management.hospitalManagement.Security;

import com.hospital.management.hospitalManagement.entity.User;
import com.hospital.management.hospitalManagement.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 1000*60*10))
                .signWith(getSecretKey())
                .compact();

    }

    public String getUsernameFromToken(String token) {
    Claims claims= Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    return claims.getSubject();

    }

    public AuthProviderType getProviderTypeFromRegistrationId(String registrationId) {
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProviderType.Google;
            case "facebook" -> AuthProviderType.Facebook;
            case "twitter" -> AuthProviderType.Twitter;
            case "email" -> AuthProviderType.Email;
            case "github" -> AuthProviderType.Github;
            default -> throw new IllegalArgumentException("unsupported Oauth2 provider"+registrationId);
        };

    }

    public String determineProviderIdFromAuth2User(OAuth2User oAuth2User,String registrationId) {
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("unsupported Oauth2 provider"+registrationId);
                throw new IllegalArgumentException("unsupported Oauth2 provider"+registrationId);
            }
        };
        if ( providerId== null || providerId.isBlank()){
            log.error("Unable to determine providerId :"+ registrationId);
            throw new IllegalArgumentException("Unable to determine providerId :");
        }
        return providerId;
    }


    public String DetermineUsernameFromOAuth2User(OAuth2User oAuth2User,String regestrationId,String providerId) {
        String email = oAuth2User.getAttribute("email");
        if(email !=null && !email.isBlank()){
            return email;
        }
        return switch (regestrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }


}
