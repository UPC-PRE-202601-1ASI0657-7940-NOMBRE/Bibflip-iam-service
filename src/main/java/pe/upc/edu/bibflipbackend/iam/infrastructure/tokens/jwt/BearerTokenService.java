package pe.upc.edu.bibflipbackend.iam.infrastructure.tokens.jwt;

import pe.upc.edu.bibflipbackend.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest request);
    String generateToken(Authentication authentication);
}
