package coffee.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    @NonNull
    @JsonIgnore
    UUID id;

    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    String confirmPassword;

    @JsonIgnore
    Collection<String> roles;
}
