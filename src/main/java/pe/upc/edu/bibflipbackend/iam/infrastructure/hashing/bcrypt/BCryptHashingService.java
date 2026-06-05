package pe.upc.edu.bibflipbackend.iam.infrastructure.hashing.bcrypt;

import pe.upc.edu.bibflipbackend.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
