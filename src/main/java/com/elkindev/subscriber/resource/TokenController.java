package com.elkindev.subscriber.resource;

import com.elkindev.subscriber.dto.TokenRequestDto;
import com.elkindev.subscriber.dto.TokenResponseDto;
import com.elkindev.subscriber.service.impl.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@Tag(name = "Authorization", description = "Authentication and authorization management")
public class TokenController {
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping()
    @Operation(summary = "Obtain token", description = "Obtain the access token for the client")
    public ResponseEntity<TokenResponseDto> getToken(@RequestBody TokenRequestDto requestDto) {
        return this.tokenService.getToken(requestDto);
    }
}
