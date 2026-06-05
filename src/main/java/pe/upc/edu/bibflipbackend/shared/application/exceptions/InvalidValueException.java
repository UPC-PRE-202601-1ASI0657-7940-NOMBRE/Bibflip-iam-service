package pe.upc.edu.bibflipbackend.shared.application.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
