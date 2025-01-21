package com.tutorial.crud.service;

import com.tutorial.crud.entity.CitaAsesorDeportivo;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.enums.EstadoCita;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.exception.CorreoEnvioException;
import com.tutorial.crud.exception.InvalidDateTimeException;
import com.tutorial.crud.repository.CitaAsesorDeportivoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CitaAsesorDeportivoService {

    private static final String CITA_ASESOR_IMAGEN = "images/cita_asesor.jpg";
    private static final String CALENDAR_LOGO = "images/calendar.png";
    private static final String TIME_LOGO = "images/time.png";
    private static final String JPG_MIME = "image/jpg";
    private static final String PNG_MIME = "image/png";
    @Autowired
    private CitaAsesorDeportivoRepository citaRepository;
    @Autowired
    private  ClienteService clienteService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public CitaAsesorDeportivo programarCita(int idCliente, String fecha, String hora, String nombreAsesor) {
        // Convertir la fecha y hora a LocalDateTime
        LocalDateTime fechaHora = convertirAFechaHora(fecha, hora);

        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado con ID: " + idCliente);
        }

        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Validar si la fecha y hora seleccionada ya pasaron
        if (fechaHora.isBefore(fechaHoraActual)) {
            throw new InvalidDateTimeException("La fecha y hora seleccionada ya ha pasado. Por favor, elige una fecha futura.");
        }

        // Enviar el correo de confirmación
        try {
            // Crear la cita
            CitaAsesorDeportivo cita = new CitaAsesorDeportivo(idCliente, fechaHora, nombreAsesor, EstadoCita.PROGRAMADA, false);
            cita = citaRepository.save(cita);
            enviarCorreoConfirmacion(cita, cliente);
            return cita;
        } catch (MessagingException e) {
            throw new CorreoEnvioException("Error al enviar el correo para la cita programada para: " + idCliente);
        }
    }

    private LocalDateTime convertirAFechaHora(String fecha, String hora) {
        String fechaHoraString = fecha + " " + hora;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(fechaHoraString, formatter);
    }

    private void enviarCorreoConfirmacion(CitaAsesorDeportivo cita, Cliente cliente) throws MessagingException {
        Context context = new Context();
        context.setVariable("nombre_cliente", cliente.getNombre());
        context.setVariable("fecha_cita", cita.getFechaHora().toLocalDate());
        context.setVariable("hora_cita", cita.getFechaHora().toLocalTime());
        context.setVariable("nombre_asesor", cita.getNombreAsesor());

        String bodyCorreo = templateEngine.process("cita-asesor", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(cliente.getEmail());
        helper.setSubject("Cita Deportiva");
        helper.setText(bodyCorreo, true);

        helper.addInline("cita_asesor", new ClassPathResource(CITA_ASESOR_IMAGEN), JPG_MIME);
        helper.addInline("calendar", new ClassPathResource(CALENDAR_LOGO), PNG_MIME);
        helper.addInline("time", new ClassPathResource(TIME_LOGO), PNG_MIME);

        mailSender.send(message);
    }
}
