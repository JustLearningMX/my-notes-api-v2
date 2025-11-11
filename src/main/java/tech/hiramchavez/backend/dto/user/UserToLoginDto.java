package tech.hiramchavez.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link tech.hiramchavez.backend.model.User}
 */
public record UserToLoginDto(

  @NotBlank(message = "Email is required")
  @Pattern(regexp = "(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}+$", message = "Enter a valid email")
  String email,

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must have at least 8 characters")
  @Size(max = 16, message = "Password must have at most 16 characters")
  String password

) implements Serializable {
}