package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.DTO.LoginDTO;
import by.kapinskiy.p2w.DTO.RegistrationDTO;
import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.services.AuthenticationService;
import by.kapinskiy.p2w.util.RegistrationDTOValidator;
import by.kapinskiy.p2w.util.exceptions.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationDTOValidator registrationDTOValidator;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(RegistrationDTOValidator registrationDTOValidator,
                          ModelMapper modelMapper,
                          AuthenticationService authenticationService)
    {
        this.registrationDTOValidator = registrationDTOValidator;
        this.modelMapper = modelMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody @Valid RegistrationDTO registration, BindingResult bindingResult) throws MethodArgumentNotValidException {
        registrationDTOValidator.validate(registration, bindingResult);

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
            for (ObjectError globalError : bindingResult.getGlobalErrors()) {
                errors.add(globalError.getObjectName() + ": " + globalError.getDefaultMessage());
            }
            throw new UserAlreadyExistsException(errors.toString());
        }
        User user = convertToUser(registration);
        String response = authenticationService.performRegistration(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO loginDTO) {
        String jwt = authenticationService.performLogin(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());
        return buildJwtResponse(jwt, "Login successful", HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<Map<String, String>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "No user authenticated"));
        }

        Map<String, String> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("message", "Current user retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "token") String token){
        authenticationService.validateVerificationToken(token);
        return ResponseEntity.ok("Email verification successful");
    }

    @GetMapping("/resend-token")
    public ResponseEntity<String> resendToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authenticationService.resendToken(authentication.getName()));
    }

    private ResponseEntity<Map<String, String>> buildJwtResponse(String jwt, String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }

    public User convertToUser(RegistrationDTO registration){
        return modelMapper.map(registration, User.class);
    }
}
