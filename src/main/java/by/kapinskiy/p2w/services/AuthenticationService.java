package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.repositories.UsersRepository;
import by.kapinskiy.p2w.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional()
public class AuthenticationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    @Value("${default_role}")
    private String defaultRole;

    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, UsersRepository usersRepository, JWTService jwtService, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public String performRegistration(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(user.getCreatedAt());
        user.setRole(defaultRole);
        usersRepository.save(user);
        return jwtService.generateToken(new UserDetails(user));
    }

    public String performLogin(String usernameOrEmail, String password){
        org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsernameOrEmail(usernameOrEmail);
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        return jwtService.generateToken(userDetails);
    }

}
