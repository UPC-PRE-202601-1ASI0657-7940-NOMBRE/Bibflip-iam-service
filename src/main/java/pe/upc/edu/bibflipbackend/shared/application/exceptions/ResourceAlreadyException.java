package pe.upc.edu.bibflipbackend.shared.application.exceptions;

public class ResourceAlreadyException extends RuntimeException {
    public ResourceAlreadyException(String message) {
        super(message);
    }
}
