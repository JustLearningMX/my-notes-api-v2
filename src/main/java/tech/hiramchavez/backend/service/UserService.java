package tech.hiramchavez.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.hiramchavez.backend.dto.user.LoggedUserDto;
import tech.hiramchavez.backend.dto.user.SignedUserDTO;
import tech.hiramchavez.backend.dto.user.UserToLoginDto;
import tech.hiramchavez.backend.dto.user.UserToSignupDTO;
import tech.hiramchavez.backend.exception.user.UserAlreadyExistsException;
import tech.hiramchavez.backend.exception.user.UserDataLoginException;
import tech.hiramchavez.backend.exception.user.UserNotFoundException;
import tech.hiramchavez.backend.mapper.user.UserMapper;
import tech.hiramchavez.backend.model.User;
import tech.hiramchavez.backend.repository.UserRepository;
import tech.hiramchavez.backend.security.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public SignedUserDTO signUp(UserToSignupDTO userToSignupDTO, HttpServletRequest request) {

        //Check if the user already exists: email, phone or username
        if (userRepository.existsByEmailAndActiveTrue(userToSignupDTO.email()))
            throw new UserAlreadyExistsException("User with email already exists");

        // Get the plain password
        String plainPassword = userToSignupDTO.password();

        // Generate the password hash
        String hashedPassword = PasswordEncoder.generatePasswordHash(plainPassword);

        // Map the DTO data to the entity
        User user = userMapper.toEntity(userToSignupDTO);

        // Assign the password hash to the entity
        user.setPassword(hashedPassword);

        // Save the user in the database
        User userDB = userRepository.save(user);

        // Map the entity data to a DTO and return it
        return userMapper.userToSignedUserDTO(userDB);
    }

    public LoggedUserDto login(UserToLoginDto userToLoginDto) {

        // Get the user email
        String userEmail = userToLoginDto.email();

        // Check if the user exists
        if (!userRepository.existsByEmailAndActiveTrue(userEmail))
            throw new UserNotFoundException("Password or email incorrect");

        // Get hashed password from the database
        String hashedPassword = userRepository.findByEmailAndActiveTrue(userEmail).getPassword();

        // Check if the password is correct
        boolean passwordMatches = PasswordEncoder.verifyPassword(userToLoginDto.password(), hashedPassword);

        if (!passwordMatches)
            throw new UserDataLoginException("Password or email incorrect");

        // Auth Credentials
        Authentication auth = new UsernamePasswordAuthenticationToken(
          userToLoginDto.email(),
          userToLoginDto.password()
        );

        // Generate the token
        User authUser = (User) authenticationManager.authenticate(auth).getPrincipal();
        String token = tokenService.generateToken(authUser);

        return new LoggedUserDto(
          false,
          authUser.getId(),
          authUser.getFirstName(),
          authUser.getLastName(),
          authUser.getEmail(),
          authUser.getActive(),
          authUser.getRole(),
          token
        );

    }

    public SignedUserDTO getUser(HttpServletRequest request) {
        User user = getUserByEmailFromDatabase(request);

        return userMapper.userToSignedUserDTO(user);
    }

    /** Get the user by phone from the database
     *
     * @param request HttpServletRequest
     * @return User
     */
    public User getUserByEmailFromDatabase(HttpServletRequest request) {
        String token = tokenService.getTokenFromHeader(request); //Get token from the header
        String userEmail = tokenService.getVerifier(token).getSubject(); //Get the user email from the token

        boolean existsUser = userRepository.existsByEmailAndActiveTrue(userEmail);

        if (!existsUser) {
            throw new UserNotFoundException("User not found in the database");
        }

        return (User) userRepository.findByEmailAndActiveTrue(userEmail);
    }
}
