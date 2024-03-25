package coffee.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    @NotNull
    @JsonIgnore
    UUID id;

    @NotNull
    String username;

    @NotNull
    String password;

    @NotNull
    String confirmPassword;

    @JsonIgnore
    Collection<String> roles;
}
