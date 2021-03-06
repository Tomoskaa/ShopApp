package mk.finki.ukim.wp.aud.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidSearchTextException extends RuntimeException {
    public InvalidSearchTextException() {
        super(String.format("Invalid Search text"));
    }
}
