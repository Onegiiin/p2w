package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.DTO.LoginDTO;
import by.kapinskiy.p2w.DTO.RegistrationDTO;
import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.services.AuthenticationService;
import by.kapinskiy.p2w.util.ErrorResponse;
import by.kapinskiy.p2w.util.RegistrationDTOValidator;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO registration, BindingResult result) {
        registrationDTOValidator.validate(registration, result);
        if (result.hasErrors()) {
            return handleBadRequest(result);
        }
        User user = convertToUser(registration);
        String jwt = authenticationService.performRegistration(user);
        return new ResponseEntity<>("JWT: " + jwt, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        String jwt = authenticationService.performLogin(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());
        return new ResponseEntity<>("JWT: " + jwt, HttpStatus.ACCEPTED);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user authenticated");
        }

        return ResponseEntity.ok(authentication.getName());
    }

    public ResponseEntity<ErrorResponse> handleBadRequest(BindingResult result) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError globalError : result.getGlobalErrors()) {
            errors.add(globalError.getObjectName() + ": " + globalError.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    public User convertToUser(RegistrationDTO registration){
        return modelMapper.map(registration, User.class);
    }
}
