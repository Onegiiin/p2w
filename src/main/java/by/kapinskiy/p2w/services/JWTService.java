package by.kapinskiy.p2w.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class JWTService {
    @Value("${jwt_secret}")
    private String SECRET;

    public String generateToken(UserDetails userDetails) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", userDetails.getUsername())
                .withClaim("authorities", authorities) // Сохраняем роли в токене
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .withIssuer("p2w")
                .sign(Algorithm.HMAC256(SECRET));
    }

    public DecodedJWT validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withSubject("User details")
                .withIssuer("p2w")
                .build();

        return verifier.verify(token);
    }
}
