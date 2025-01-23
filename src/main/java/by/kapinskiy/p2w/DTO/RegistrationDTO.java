package by.kapinskiy.p2w.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class RegistrationDTO {
    @Column(name = "email")
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email should be in format username@example.com")
    private String email;

    @Column(name = "id")
    @NotBlank(message = "Username can't be empty")
    @Length(min = 3, max = 25, message = "Username should be between 3 and 25 characters")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Password can't be empty")
    @Length(min = 2, message = "Password should be at least 8 symbols")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
