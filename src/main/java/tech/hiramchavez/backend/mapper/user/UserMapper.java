package tech.hiramchavez.backend.mapper.user;

import org.mapstruct.*;
import tech.hiramchavez.backend.dto.user.LoggedUserDto;
import tech.hiramchavez.backend.dto.user.SignedUserDTO;
import tech.hiramchavez.backend.dto.user.UserToLoginDto;
import tech.hiramchavez.backend.dto.user.UserToSignupDTO;
import tech.hiramchavez.backend.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserToSignupDTO userToSignupDTO);

    UserToSignupDTO userToUserToSignupDTO(User user);

    User toEntity(SignedUserDTO signedUserDTO);

    SignedUserDTO userToSignedUserDTO(User user);

    User toEntity(UserToLoginDto userToLoginDto);

    UserToLoginDto userToUserToLoginDto(User user);

    User toEntity(LoggedUserDto loggedUserDto);

    LoggedUserDto userToLoggedUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(SignedUserDTO signedUserDTO, @MappingTarget User user);
}