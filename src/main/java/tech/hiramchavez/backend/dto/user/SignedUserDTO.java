package tech.hiramchavez.backend.dto.user;

import tech.hiramchavez.backend.model.UserRole;

import java.io.Serializable;

/**
 * DTO for {@link tech.hiramchavez.backend.model.User}
 */
public record SignedUserDTO(

  boolean isError,
  Long id,
  String firstName,
  String lastName,
  String email,
  Boolean active,
  UserRole role

) implements Serializable {

    public SignedUserDTO {
        isError = false;
    }

}