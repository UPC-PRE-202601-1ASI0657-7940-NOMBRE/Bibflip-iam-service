package pe.upc.edu.bibflipbackend.shared.application.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
