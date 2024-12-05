package com.elkindev.subscriber.service.impl;

import com.elkindev.subscriber.dto.TokenRequestDto;
import com.elkindev.subscriber.dto.TokenResponseDto;
import com.elkindev.subscriber.persistence.entities.ApplicationEntity;
import com.elkindev.subscriber.persistence.entities.RoleEntity;
import com.elkindev.subscriber.persistence.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final ApplicationRepository applicationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;

    @SneakyThrows
    public ResponseEntity<TokenResponseDto> getToken(TokenRequestDto dto) {

        var appFromDb = applicationRepository.findByName(dto.getAppName());

        if (appFromDb.isEmpty() /*|| !isSubscriberCorrect(dto, appFromDb.get(), bCryptPasswordEncoder)*/) {
            log.error("client_id or secret is invalid!");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var now = Instant.now();
        var expiresIn = 300L; // 5 minutos
        var scopes = appFromDb.get().getRoleEntities()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("nebula-auth")
                .subject(appFromDb.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        // Generar el token usando el encoder con la llave secreta (HS256)
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        var tokenResult = TokenResponseDto.builder()
                .accessToken(jwtValue)
                .expiresIn(expiresIn)
                .build();
        return new ResponseEntity<>(tokenResult, HttpStatus.OK);
    }

    public boolean isSubscriberCorrect(TokenRequestDto tokenRequestDto, ApplicationEntity applicationEntity, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(tokenRequestDto.getSecret(), applicationEntity.getClientSecret());
    }
}
