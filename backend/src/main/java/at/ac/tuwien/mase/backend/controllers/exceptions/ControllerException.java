package at.ac.tuwien.mase.backend.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lobmaier on 14.11.2015.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ControllerException extends Exception {
    public ControllerException(String message) {
        super(message);
    }
    public ControllerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
