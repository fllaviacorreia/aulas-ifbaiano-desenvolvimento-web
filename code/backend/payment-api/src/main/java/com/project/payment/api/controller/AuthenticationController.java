package com.project.payment.api.controller;

import com.project.payment.domain.dto.*;
import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("authenticate") // public
    public ResponseEntity<ResponseAuth> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
       return authenticationService.authenticate(loginDTO);
    }

    @PostMapping("register") // public
    public ResponseEntity<ResponseAuth> register(@RequestBody RegisterDTO userDTO) {
        System.out.println(userDTO.getClienteModel().getNome());
        return authenticationService.register(userDTO);
    }

    @GetMapping("profile")
    public ProfileDTO getProfile(Authentication authentication) {
        return authenticationService.getCurrentUser(authentication.getName());
    }

    @GetMapping("profile/parcelamentos")
    public ResponseEntity<List<ParcelamentoModel>> getParcelamentos(Authentication authentication) {
        return authenticationService.getParcelamentos(authentication.getName());
    }

    @PutMapping("profile")
    public ResponseEntity<String> putProfile(Authentication authentication, @Valid @RequestBody UpdateProfileDTO updateProfileDTO) {
        return authenticationService.putCurrentUser(authentication.getName(), updateProfileDTO);
    }

    @PutMapping("pass")
    @PreAuthorize("#loginDTO.email == authentication.name")
    public ResponseEntity<String> putPassword(Authentication authentication, @Valid @RequestBody LoginDTO loginDTO) {

            return authenticationService.putPassword(authentication.getName(), loginDTO.getPassword());

    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capture(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
