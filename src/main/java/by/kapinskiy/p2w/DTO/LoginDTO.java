package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class LoginDTO {
    @NotBlank(message = "Identifier can't be empty")
    private String usernameOrEmail;

    @NotBlank(message = "Password can't be empty")
    @Length(min = 2, message = "Password should be at least 8 symbols")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
}
