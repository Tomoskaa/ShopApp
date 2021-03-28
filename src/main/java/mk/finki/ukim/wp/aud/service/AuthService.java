package mk.finki.ukim.wp.aud.service;

import mk.finki.ukim.wp.aud.model.User;

public interface AuthService {

    User login(String username, String password);

}
