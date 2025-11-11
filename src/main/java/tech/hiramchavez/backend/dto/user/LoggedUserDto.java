package tech.hiramchavez.backend.dto.user;

import tech.hiramchavez.backend.model.UserRole;

import java.io.Serializable;

/**
 * DTO for {@link tech.hiramchavez.backend.model.User}
 */
public record LoggedUserDto(

    boolean isError,
    Long id,
    String firstName,
    String lastName,
    String email,
    Boolean active,
    UserRole role,
    String token

) implements Serializable {

    public LoggedUserDto {
        isError = false;
    }

}