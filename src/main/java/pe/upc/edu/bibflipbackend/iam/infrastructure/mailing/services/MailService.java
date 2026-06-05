package pe.upc.edu.bibflipbackend.iam.infrastructure.mailing.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender sender;

    @Value("${spring.mail.from}")
    private String fromEmail;

    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendPasswordResetCode(String to, String code) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Recuperación de contraseña - BibFlip");

            String htmlContent = ("""
                <div style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #ffffff; padding: 30px;">
                    <div style="max-width: 600px; margin: auto; background: #fff; border-radius: 10px;
                                border: 1px solid #ddd; padding: 25px; box-shadow: 0 4px 10px rgba(0,0,0,0.08);">
        
                        <!-- Logo -->
                        <div style="text-align: center; margin-bottom: 20px;">
                            <img src="https://i.postimg.cc/rF2VCQkK/bibflip-circular.png"
                                 alt="BibFlip Logo" width="90" height="90" style="border-radius: 50%%;"/>
                        </div>

                        <!-- Título -->
                        <h2 style="color: #4b2e14; text-align: center; margin-top: 0;">Recuperación de Contraseña</h2>

                        <!-- Texto principal -->
                        <p style="font-size: 15px; color: #333333; text-align: center; line-height: 1.6;">
                            Hola, hemos recibido una solicitud para restablecer tu contraseña en <strong style="color: #4b2e14;">BibFlip</strong>.
                        </p>
                        <p style="font-size: 15px; color: #333333; text-align: center; line-height: 1.6;">
                            Tu código de recuperación es:
                        </p>

                        <!-- Código -->
                        <div style="text-align: center; margin: 25px 0;">
                            <span style="font-size: 36px; font-weight: bold; color: #4b2e14;
                                         letter-spacing: 3px;">%s</span>
                        </div>

                        <!-- Expiración -->
                        <p style="font-size: 14px; color: #555; text-align: center;">
                            Este código expira en <strong>15 minutos</strong>.
                        </p>

                        <hr style="margin: 25px 0; border: none; border-top: 1px solid #eee;">

                        <!-- Footer -->
                        <p style="font-size: 12px; color: #888888; text-align: center;">
                            Si no solicitaste este cambio, puedes ignorar este correo.<br>
                            © 2025 <strong style="color:#4b2e14;">BibFlip</strong>. Todos los derechos reservados.
                        </p>
                    </div>
                </div>
                """.stripIndent()).formatted(code);

            helper.setText(htmlContent, true);
            sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de recuperación", e);
        }
    }
}