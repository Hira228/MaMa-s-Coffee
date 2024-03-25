package coffee.service;

import coffee.web.dto.AuthenticationRequest;
import coffee.web.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<?> registerUser(UserDTO userDTO);
    ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest);
}