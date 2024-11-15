package com.tutorial.crud.correo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class OpenPayEmailService {
    private static final String OPENPAY_LOGO_IMAGE = "images/open-pay-logo.png";
    private static final String PNG_MIME = "image/png";

    @Value("${openpay.urlGeneratePDF}")
    private String urlGeneratePDF;

    @Value("${openpay.merchant-id}")
    private String merchantId;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    // Método para enviar el correo
    public void enviarCorreo(String destinatario, String nombreCliente, String numeroReferencia) throws MessagingException {
        // Construir la URL completa para el PDF
        String urlConReferencia = urlGeneratePDF.replace("{merchantId}", merchantId) + "/" + numeroReferencia;
        // Crear el modelo para Thymeleaf
        Context context = new Context();
        context.setVariable("nombreCliente", nombreCliente);  // Añadir el nombre del cliente
        context.setVariable("urlReferencia", urlConReferencia); // Añadir la URL de la referencia

        // Procesar la plantilla Thymeleaf
        String htmlContent = templateEngine.process("correo-referencia-openpay", context);

        // Crear el mensaje MIME
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // Establecer el destinatario y el asunto
        helper.setTo(destinatario);
        helper.setSubject("Tu Referencia de Pago OpenPay");

        // Establecer el contenido del mensaje como HTML
        helper.setText(htmlContent, true);

        helper.addInline("open-pay-logo", new ClassPathResource(OPENPAY_LOGO_IMAGE), PNG_MIME);

        // Enviar el correo
        mailSender.send(mimeMessage);
    }
}
