package coffee.web.controller;

import coffee.service.AuthenticationService;
import coffee.web.dto.AuthenticationRequest;
import coffee.web.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUP(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        return authenticationService.registerUser(userDTO, bindingResult);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult bindingResult) {
        return authenticationService.authenticateUser(authenticationRequest, bindingResult);
    }

    @GetMapping("/validate")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<?> validateToken() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-id")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<?> getToken(HttpServletRequest request) {
        return authenticationService.getId(request);
    }
}
