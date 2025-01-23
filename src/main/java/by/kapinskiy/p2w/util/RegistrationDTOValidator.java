package by.kapinskiy.p2w.util;

import by.kapinskiy.p2w.DTO.RegistrationDTO;
import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class RegistrationDTOValidator implements Validator {
    private final UsersService usersService;

    public RegistrationDTOValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            RegistrationDTO registrationDTO = (RegistrationDTO) target;

            String email = registrationDTO.getEmail();
            String username = registrationDTO.getUsername();

            if (usersService.findByEmail(email).isPresent()) {
                errors.rejectValue("email", "email.alreadyExists", "User with such email already exists");
            }

            if (usersService.findByUsername(username).isPresent()) {
                errors.rejectValue("username", "username.alreadyExists", "Username is already taken");
            }
        }
    }
}
