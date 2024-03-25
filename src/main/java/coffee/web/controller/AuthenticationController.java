package coffee.web.controller;

import coffee.exceptions.AuthError;
import coffee.service.AuthenticationService;
import coffee.service.UserService;
import coffee.utils.JwtTokenUtils;
import coffee.web.dto.AuthenticationDTO;
import coffee.web.dto.AuthenticationRequest;
import coffee.web.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/mama")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getMama() {
        return "mama";
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUP(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        return authenticationService.registerUser(userDTO, bindingResult);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult bindingResult) {
        return authenticationService.authenticateUser(authenticationRequest, bindingResult);
    }
}
