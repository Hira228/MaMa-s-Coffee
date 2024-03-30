package coffee.service;

import coffee.exceptions.AuthError;
import coffee.exceptions.BadRequestException;
import coffee.exceptions.UnauthorizedException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> registerUser(UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new AuthError(HttpStatus.BAD_REQUEST.value(), "Please fill in all fields for registration."), HttpStatus.BAD_REQUEST);
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords don't match");
        }
        boolean result = userService.createUser(userDTO);
        if (result) {
            return new ResponseEntity<>(new AuthError(HttpStatus.CREATED.value(), "The account was successfully created."), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new AuthError(HttpStatus.BAD_REQUEST.value(), "There's already a user with that username. Think of another one."), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new AuthError(HttpStatus.BAD_REQUEST.value(), "Please complete all fields to log in."), HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationDTO(jwtTokenUtils.generateToken(userDetails)));
        } catch (BadCredentialsException err) {
            throw new UnauthorizedException("Invalid login or password");
        }
    }
}
