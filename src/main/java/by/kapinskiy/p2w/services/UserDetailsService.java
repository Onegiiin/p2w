package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.User;
import by.kapinskiy.p2w.repositories.UsersRepository;
import by.kapinskiy.p2w.util.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UsersRepository usersRepository;

    public UserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException("User with username \"" + username + "\" not found"));

        return new by.kapinskiy.p2w.security.UserDetails(user);
    }

    public UserDetails loadUserByUsernameOrEmail(String usernameOrEmail) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(usernameOrEmail)
                .orElseGet(() -> usersRepository.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new UserNotFoundException("User with identifier \"" + usernameOrEmail + "\" not found")));

        return new by.kapinskiy.p2w.security.UserDetails(user);
    }
}
