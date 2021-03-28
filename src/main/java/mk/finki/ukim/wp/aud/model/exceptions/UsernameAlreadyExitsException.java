package mk.finki.ukim.wp.aud.model.exceptions;

public class UsernameAlreadyExitsException extends RuntimeException {

    public UsernameAlreadyExitsException(String username) {
        super(String.format("User with username %s already exists.", username));
    }
}
