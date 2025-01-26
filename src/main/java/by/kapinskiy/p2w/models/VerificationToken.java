package by.kapinskiy.p2w.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @Column(name = "token")
    String token;

    @OneToOne
    @JoinColumn(name = "user_account", referencedColumnName = "id")
    User user;

    @Column(name = "expiration_date")
    private Date expirationDate;

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
