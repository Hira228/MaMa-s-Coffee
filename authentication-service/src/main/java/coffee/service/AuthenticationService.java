package coffee.service;

import coffee.web.dto.AuthenticationRequest;
import coffee.web.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface AuthenticationService {
    ResponseEntity<?> registerUser(UserDTO userDTO, BindingResult bindingResult);

    ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest, BindingResult bindingResult);

}