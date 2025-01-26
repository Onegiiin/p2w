package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.models.VerificationToken;
import by.kapinskiy.p2w.repositories.RolesRepository;
import by.kapinskiy.p2w.repositories.UsersRepository;
import by.kapinskiy.p2w.repositories.VerificationTokensRepository;
import by.kapinskiy.p2w.util.exceptions.InvalidTokenException;
import by.kapinskiy.p2w.util.exceptions.NotFoundException;
import by.kapinskiy.p2w.util.exceptions.UserAlreadyVerifiedException;
import by.kapinskiy.p2w.util.exceptions.UserNotVerifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public class AuthenticationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final RolesRepository rolesRepository;
    private final EmailService emailService;
    private final VerificationTokensRepository verificationTokensRepository;
    @Value("${default_role}")
    private String defaultRole;

    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 UsersRepository usersRepository,
                                 JWTService jwtService,
                                 UserDetailsService userDetailsService,
                                 RolesRepository rolesRepository,
                                 EmailService emailService,
                                 VerificationTokensRepository verificationTokensRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.rolesRepository = rolesRepository;
        this.emailService = emailService;
        this.verificationTokensRepository = verificationTokensRepository;
    }

    private String generateUniqueToken() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (verificationTokensRepository.existsById(token));  // Проверяем на существование
        return token;
    }

    private void deleteOldToken(User user){
        VerificationToken oldToken = user.getVerificationToken();
        if (oldToken != null) {
            verificationTokensRepository.delete(oldToken);
            user.setVerificationToken(null);
        }
    }

    public String generateAndSendVerificationToken(User user) {
        if (!user.isEnabled()) {
            deleteOldToken(user);
            VerificationToken token = new VerificationToken();
            token.setUser(user);
            token.setExpirationDate(Date.from(ZonedDateTime.now().plusHours(1).toInstant()));
            token.setToken(generateUniqueToken());
            verificationTokensRepository.save(token);
            String confirmationUrl = "http://localhost:8080/auth/verify-email?token=" + token.getToken();
            emailService.sendEmail(user.getEmail(), "Email Verification", "Click the link to verify your email: " + confirmationUrl);
            return "Confirmation link was sent to your email";
        } else {
            throw new UserAlreadyVerifiedException("User is already verified");
        }
    }

    @Transactional()
    public String performRegistration(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(user.getCreatedAt());
        user.setRoles(List.of(rolesRepository.findByName(defaultRole).orElseThrow(() -> new NotFoundException("default role not found"))));
        user.setEnabled(false);

        usersRepository.save(user);
        return generateAndSendVerificationToken(user);
    }

    public String performLogin(String usernameOrEmail, String password){
        org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsernameOrEmail(usernameOrEmail);
        if (!userDetails.isEnabled())
            throw new UserNotVerifiedException("Account is not verified");
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        return jwtService.generateToken(userDetails);
    }

    @Transactional()
    public void validateVerificationToken(String token) {
        User user = usersRepository.findByVerificationToken_Token(token).orElseThrow(() -> new InvalidTokenException("Invalid token"));
        if (user.getVerificationToken().getExpirationDate().before(new Date())){
            throw new InvalidTokenException("Token is expired");
        } else {
            user.setEnabled(true);
            verificationTokensRepository.delete(user.getVerificationToken());
            user.setVerificationToken(null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional()
    public String resendToken(String username) {
        User user = usersRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("user not found"));
        if (!user.isEnabled()) {
           return generateAndSendVerificationToken(user);
        } else {
            throw new UserAlreadyVerifiedException("User is already verified");
        }
    }

}
