package mk.finki.ukim.wp.aud.service;

import mk.finki.ukim.wp.aud.model.Role;
import mk.finki.ukim.wp.aud.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname,
                  Role role);
}
