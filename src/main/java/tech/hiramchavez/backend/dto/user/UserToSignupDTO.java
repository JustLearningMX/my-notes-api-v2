package tech.hiramchavez.backend.dto.user;

import jakarta.validation.constraints.*;
import tech.hiramchavez.backend.model.UserRole;

import java.io.Serializable;

/**
 * DTO for {@link tech.hiramchavez.backend.model.User}
 */
public record UserToSignupDTO(

  @NotBlank(message = "First Name is required")
  @Size(min = 3, message = "First Name must have at least 3 characters")
  String firstName,

  @NotBlank(message = "Last Name is required")
  @Size(min = 3, message = "Last Name must have at least 3 characters")
  String lastName,

  @NotBlank(message = "Email is required")
  @Pattern(regexp = "(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}+$", message = "Enter a valid email")
  String email,

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must have at least 8 characters")
  @Size(max = 16, message = "Password must have at most 16 characters")
  String password,

  @NotNull(message = "Active status is required")
  Boolean active,

  @NotNull(message = "User role is required")
  UserRole role

) implements Serializable {

    public UserToSignupDTO {
        active = true;

        if (role == null) {
            role = UserRole.USER;
        }
    }
}