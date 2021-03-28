package mk.finki.ukim.wp.aud.service.impl;


import mk.finki.ukim.wp.aud.jpa.UserRepository;
import mk.finki.ukim.wp.aud.model.Role;
import mk.finki.ukim.wp.aud.model.User;
import mk.finki.ukim.wp.aud.model.exceptions.InvalidUsernameOrPasswordException;
import mk.finki.ukim.wp.aud.model.exceptions.PasswordsDoNotMatchException;
import mk.finki.ukim.wp.aud.model.exceptions.UsernameAlreadyExitsException;
import mk.finki.ukim.wp.aud.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExitsException(username);

        User user = new User(username, passwordEncoder.encode(password), name, surname, userRole);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

}
