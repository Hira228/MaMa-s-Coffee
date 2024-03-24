package coffee.web.controller;

import coffee.exceptions.AuthError;
import coffee.service.UserService;
import coffee.utils.JwtTokenUtils;
import coffee.web.dto.AuthenticationDTO;
import coffee.web.dto.AuthenticationRequest;
import coffee.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    private final String SIGN_IN_ROOT = "/signIn";
    private final String SIGN_UP_ROOT = "/signUp";

    @GetMapping("/mama")
    public String getMama() {
        return "mama";
    }
    @PostMapping(SIGN_UP_ROOT)
    public ResponseEntity<?> signUP(@RequestBody
                                                    UserDTO userDTO) {
        boolean result = userService.createUser(userDTO);
        if(result) {
            UserDetails userDetails = userService.loadUserByUsername(userDTO.getUsername());
            String token = jwtTokenUtils.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationDTO(token));
        } else {
            return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(),
                    "User with that username:" +
                           userDTO.getUsername() + "has already been created. Use the other one."), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(SIGN_IN_ROOT)
    public ResponseEntity<?> signUP(@RequestBody
                                                    AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException err) {
            return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(),
                    "Invalid login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationDTO(token));
    }
}
