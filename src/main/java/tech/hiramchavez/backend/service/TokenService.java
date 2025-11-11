package tech.hiramchavez.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.hiramchavez.backend.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.jwt_secret}")
    private String apiSecret;

    @Value("${api.security.issuer}")
    private String apiIssuer;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            return JWT.create()
              .withIssuer(apiIssuer)
              .withSubject(user.getEmail())
              .withClaim("id", user.getId())
              .withClaim("ROLE", user.getRole().name())
              .withClaim("email", user.getEmail())
              .withExpiresAt(getExpirationTime())
              .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating token. " + e.getMessage());
        }
    }

    public DecodedJWT getVerifier(String token) {

        if (token == null || token.isEmpty())
            throw new RuntimeException("There is no token!");

        DecodedJWT verifier = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // token signature validation

            verifier = JWT.require(algorithm)
              .withIssuer(apiIssuer)
              .build()
              .verify(token);

            verifier.getSubject();

        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new JWTVerificationException(e.getMessage());
        }

        if (verifier.getSubject() == null)
            throw new RuntimeException("Invalid token!");

        return verifier;
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        //Get authHeader from header
        String authHeader = request.getHeader("Authorization");

        // Check if authHeader is null or "null"
        if (authHeader != null && authHeader.equals("null"))
            authHeader = null;

        // Check if authHeader is null
        if (authHeader != null)
            return authHeader.replace("Bearer ", ""); //token'

        return null;
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-06:00"));
    }

}
