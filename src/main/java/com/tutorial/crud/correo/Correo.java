package com.tutorial.crud.correo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.util.Properties;

public class Correo {
	
	private String correoEnvia;
	private String contrasena;
	private String destinatario;
	private String copiaoculta;
	 
	public Correo() {
		super();
	}

	public Correo(String correoEnvia, String contrasena, String destinatario,String copiaOculta) {
		super();
		this.correoEnvia = correoEnvia;
		this.contrasena = contrasena;
		this.destinatario = destinatario;
		this.copiaoculta=copiaOculta;
	}
	public Correo(String correoEnvia, String contrasena, String destinatario) {
		super();
		this.correoEnvia = correoEnvia;
		this.contrasena = contrasena;
		this.destinatario = destinatario;
	}

	public String getCopiaoculta() {
		return copiaoculta;
	}

	public void setCopiaoculta(String copiaoculta) {
		this.copiaoculta = copiaoculta;
	}

	public String getCorreoEnvia() {
		return correoEnvia;
	}

	public void setCorreoEnvia(String correoEnvia) {
		this.correoEnvia = correoEnvia;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public void enviar_correo(String Titular, int idEmpleado) {                                         
        Calendar fecha = Calendar.getInstance();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH) + 1;
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            int minuto = fecha.get(Calendar.MINUTE);
            int segundo = fecha.get(Calendar.SECOND);
            
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
 
       
        Session sesion = Session.getDefaultInstance(propiedad);	
        
       
        
        String asunto = "Solicitud de vacaciones nueva, "+ dia + "/" + (mes) + "/" + año +" - "+ hora + ":" + minuto ;
        String mensaje = "<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
        		+ "        <title>Solicitud Vacaciones</title>\r\n"
        		+ "        <style type=\"text/css\">\r\n"
        		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
        		+ "            * 	 { margin: 0;padding: 0; }\r\n"
        		+ "            body { font-size: 14px; }\r\n"
        		+ "\r\n"
        		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
        		+ "\r\n"
        		+ "            h3 {\r\n"
        		+ "                margin-bottom: 10px;\r\n"
        		+ "                font-size: 15px;\r\n"
        		+ "                font-weight: 600;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps {\r\n"
        		+ "                width: 496px; \r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                box-sizing: border-box;\r\n"
        		+ "                padding: 0 45px;\r\n"
        		+ "                margin: 40px auto;\r\n"
        		+ "                overflow: hidden;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                font-family: 'Open Sans', sans-serif;\r\n"
        		+ "                color: #4f5365;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reminder {\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -1px;\r\n"
        		+ "                padding: 9px 0 10px;\r\n"
        		+ "                font-size: 11px;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                color: #ffffff;\r\n"
        		+ "                background: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info {\r\n"
        		+ "                margin-top: 26px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info:after {\r\n"
        		+ "                visibility: hidden;\r\n"
        		+ "                display: block;\r\n"
        		+ "                font-size: 0;\r\n"
        		+ "                content: \" \";\r\n"
        		+ "                clear: both;\r\n"
        		+ "                height: 0;\r\n"
        		+ "\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand {\r\n"
        		+ "                width: 45%;\r\n"
        		+ "                float: left;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand img {\r\n"
        		+ "                max-width: 150px;\r\n"
        		+ "                margin-top: 2px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount {\r\n"
        		+ "                width: 55%;\r\n"
        		+ "                float: right;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 {\r\n"
        		+ "                font-size: 36px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                line-height: 24px;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 sup {\r\n"
        		+ "                font-size: 16px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -2px\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount p {\r\n"
        		+ "                font-size: 10px;\r\n"
        		+ "                line-height: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reference {\r\n"
        		+ "                margin-top: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            h1 {\r\n"
        		+ "                font-size: 27px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                margin-top: -1px;\r\n"
        		+ "                padding: 6px 0 7px;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-instructions {\r\n"
        		+ "                margin: 32px -45px 0;\r\n"
        		+ "                padding: 32px 45px 45px;\r\n"
        		+ "                border-top: 1px solid #b0afb5;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            ol {\r\n"
        		+ "                margin: 17px 0 0 16px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            li + li {\r\n"
        		+ "                margin-top: 10px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            a {\r\n"
        		+ "                color: #1155cc;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-footnote {\r\n"
        		+ "                margin-top: 22px;\r\n"
        		+ "                padding: 22px 20 24px;\r\n"
        		+ "                color: #108f30;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                border: 1px solid #108f30;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #ffffff;\r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div class=\"opps\">\r\n"
        		+ "            <div class=\"opps-header\">\r\n"
        		+ "                <div class=\"opps-reminder\">Notificacion de Solicitud Vacaciones</div>\r\n"
        		+ "                <div class=\"opps-info\">\r\n"
        		+ "                    <div class=\"opps-brand\"><img src=\"https://www.clubalpha.com.mx/images/logo_positivo2.png\" alt=\"OXXOPay\"></div>\r\n"
        		+ "                    <div class=\"opps-ammount\">\r\n"
        		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
        		+ "                        <h2>"+idEmpleado+"</h2>\r\n"
        		+ "                        <p>Tienes una Solicitud de Vacaciones Pendiente</p>\r\n"
        		+ "                    </div>\r\n"
        		+ "                </div>\r\n"
        		+ "                <div class=\"opps-reference\">\r\n"
        		+ "                    <h3>Empleado</h3>\r\n"
        		+ "                    <h1>"+Titular+"</h1> \r\n"
        		+ "                </div><br>\r\n"
        		+ "            </div>\r\n"
        		+ "            <div class=\"opps-instructions\">\r\n"
        		+ "                <h3>Instrucciones</h3>\r\n"
        		+ "                <ol>\r\n"
        		+ "                    <li>Verifica la Solicitud. <a href=\"http://192.168.20.102/rh/consulta/empleados/"+idEmpleado+"\" target=\"_blank\">Encuentrala aqui</a>.</li>\r\n"
        		+ "                    <li>No olvides colocar tus <strong>Credenciales</strong>.</li>\r\n"
        		+ "                    <li>Solicitud realizada el dia <strong>"+ dia + "/" + (mes) + "/" + año +" - "+ hora + ":" + minuto +"</strong></li>\r\n"
        		+ "                </ol>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
         MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom((Address)new InternetAddress(this.correoEnvia));
            mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario), new InternetAddress(this.copiaoculta) });
            mail.setSubject(asunto);
            mail.setContent(mensaje, "text/html; charset=UTF-8");
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
            transporte.close();           
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	public void enviar_correo2(String asunto,int idCliente,String nombre,String horaEntrada,String horaSalida,String texto,String cabecera) {                                         
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
 
       
        Session sesion = Session.getDefaultInstance(propiedad);	
        
       
        
        
        String mensaje = "<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
        		+ "        <title>Incidencia</title>\r\n"
        		+ "        <style type=\"text/css\">\r\n"
        		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
        		+ "            * 	 { margin: 0;padding: 0; }\r\n"
        		+ "            body { font-size: 14px; }\r\n"
        		+ "\r\n"
        		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
        		+ "\r\n"
        		+ "            h3 {\r\n"
        		+ "                margin-bottom: 10px;\r\n"
        		+ "                font-size: 15px;\r\n"
        		+ "                font-weight: 600;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps {\r\n"
        		+ "                width: 496px; \r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                box-sizing: border-box;\r\n"
        		+ "                padding: 0 45px;\r\n"
        		+ "                margin: 40px auto;\r\n"
        		+ "                overflow: hidden;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                font-family: 'Open Sans', sans-serif;\r\n"
        		+ "                color: #4f5365;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reminder {\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -1px;\r\n"
        		+ "                padding: 9px 0 10px;\r\n"
        		+ "                font-size: 11px;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                color: #ffffff;\r\n"
        		+ "                background: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info {\r\n"
        		+ "                margin-top: 26px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info:after {\r\n"
        		+ "                visibility: hidden;\r\n"
        		+ "                display: block;\r\n"
        		+ "                font-size: 0;\r\n"
        		+ "                content: \" \";\r\n"
        		+ "                clear: both;\r\n"
        		+ "                height: 0;\r\n"
        		+ "\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand {\r\n"
        		+ "                width: 45%;\r\n"
        		+ "                float: left;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand img {\r\n"
        		+ "                max-width: 150px;\r\n"
        		+ "                margin-top: 2px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount {\r\n"
        		+ "                width: 55%;\r\n"
        		+ "                float: right;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 {\r\n"
        		+ "                font-size: 36px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                line-height: 24px;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 sup {\r\n"
        		+ "                font-size: 16px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -2px\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount p {\r\n"
        		+ "                font-size: 10px;\r\n"
        		+ "                line-height: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reference {\r\n"
        		+ "                margin-top: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            h1 {\r\n"
        		+ "                font-size: 27px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                margin-top: -1px;\r\n"
        		+ "                padding: 6px 0 7px;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-instructions {\r\n"
        		+ "                margin: 32px -45px 0;\r\n"
        		+ "                padding: 32px 45px 45px;\r\n"
        		+ "                border-top: 1px solid #b0afb5;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            ol {\r\n"
        		+ "                margin: 17px 0 0 16px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            li + li {\r\n"
        		+ "                margin-top: 10px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            a {\r\n"
        		+ "                color: #1155cc;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-footnote {\r\n"
        		+ "                margin-top: 22px;\r\n"
        		+ "                padding: 22px 20 24px;\r\n"
        		+ "                color: #108f30;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                border: 1px solid #108f30;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #ffffff;\r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div class=\"opps\">\r\n"
        		+ "            <div class=\"opps-header\">\r\n"
        		+ "                <div class=\"opps-reminder\">Notificación de Incidencia</div>\r\n"
        		+ "                <div class=\"opps-info\">\r\n"
        		+ "                    <div class=\"opps-brand\"><img src=\"https://www.clubalpha.com.mx/images/logo_positivo2.png\" alt=\"OXXOPay\"></div>\r\n"
        		+ "                    <div class=\"opps-ammount\">\r\n"
        		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
        		+ "                        <h2>"+idCliente+"  </h2>\r\n"
        		+ "                        <p>Le informamos que se registró la "+cabecera+" incidencia por exceder las 4 horas por ingreso, en el estacionamiento de Club Alpha 3.</p>\r\n"
        		+ "                        <!-- <p>Se ha detectado que ha estado mas de 4 horas en el estacionamiento</p> -->\r\n"
        		+ "                    </div>\r\n"
        		+ "                </div>\r\n"
        		+ "                <div class=\"opps-reference\">\r\n"
        		+ "                    <h3>Cliente:</h3>\r\n"
        		+ "                    <h1>"+nombre+"</h1> \r\n"
        		+ "                </div>\r\n"
        		+ "            </div>\r\n"
        		+ "            <br>\r\n"
        		+ "            <p>Fecha y hora  de entrada "+horaEntrada+" – Fecha y hora de salida: "+horaSalida+"</p>\r\n"
        		+ "            <div class=\"opps-instructions\">\r\n"
        		+ "                <h3>RECUERDE:</h3>\r\n"
        		+ "                <ol>\r\n"
        		+ 					texto
        		+ "                </ol>\r\n"
        		+ "                <br>\r\n"
        		+ "                <small>\r\n"
        		+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes 222 235 17 35 Ext. 101\r\n"
        		+ "                    Atentamente:\r\n"
        		+ "                    Club Alpha 3\r\n"
        		+ "                    <br>\r\n"
        		+ "                    <br>\r\n"
        		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de Club Alpha, disponible en www.clubalpha.com.mx\r\n"
        		+ "                </small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom((Address)new InternetAddress(this.correoEnvia));
            mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario), new InternetAddress(this.copiaoculta) });
            mail.setSubject(asunto);
            mail.setContent(mensaje, "text/html; charset=UTF-8");
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
            transporte.close();           
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	public void enviar_correo3(String asunto,int idCliente,String nombre,String horaEntrada,String horaSalida) {    
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
 
       
        Session sesion = Session.getDefaultInstance(propiedad);	
        
       
        
        
        String mensaje = "<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
        		+ "        <title>Incidencia</title>\r\n"
        		+ "        <style type=\"text/css\">\r\n"
        		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
        		+ "            * 	 { margin: 0;padding: 0; }\r\n"
        		+ "            body { font-size: 14px; }\r\n"
        		+ "\r\n"
        		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
        		+ "\r\n"
        		+ "            h3 {\r\n"
        		+ "                margin-bottom: 10px;\r\n"
        		+ "                font-size: 15px;\r\n"
        		+ "                font-weight: 600;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps {\r\n"
        		+ "                width: 496px; \r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                box-sizing: border-box;\r\n"
        		+ "                padding: 0 45px;\r\n"
        		+ "                margin: 40px auto;\r\n"
        		+ "                overflow: hidden;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                font-family: 'Open Sans', sans-serif;\r\n"
        		+ "                color: #4f5365;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reminder {\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -1px;\r\n"
        		+ "                padding: 9px 0 10px;\r\n"
        		+ "                font-size: 11px;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                color: #ffffff;\r\n"
        		+ "                background: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info {\r\n"
        		+ "                margin-top: 26px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info:after {\r\n"
        		+ "                visibility: hidden;\r\n"
        		+ "                display: block;\r\n"
        		+ "                font-size: 0;\r\n"
        		+ "                content: \" \";\r\n"
        		+ "                clear: both;\r\n"
        		+ "                height: 0;\r\n"
        		+ "\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand {\r\n"
        		+ "                width: 45%;\r\n"
        		+ "                float: left;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand img {\r\n"
        		+ "                max-width: 150px;\r\n"
        		+ "                margin-top: 2px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount {\r\n"
        		+ "                width: 55%;\r\n"
        		+ "                float: right;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 {\r\n"
        		+ "                font-size: 36px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                line-height: 24px;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 sup {\r\n"
        		+ "                font-size: 16px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -2px\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount p {\r\n"
        		+ "                font-size: 10px;\r\n"
        		+ "                line-height: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reference {\r\n"
        		+ "                margin-top: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            h1 {\r\n"
        		+ "                font-size: 27px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                margin-top: -1px;\r\n"
        		+ "                padding: 6px 0 7px;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-instructions {\r\n"
        		+ "                margin: 32px -45px 0;\r\n"
        		+ "                padding: 32px 45px 45px;\r\n"
        		+ "                border-top: 1px solid #b0afb5;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            ol {\r\n"
        		+ "                margin: 17px 0 0 16px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            li + li {\r\n"
        		+ "                margin-top: 10px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            a {\r\n"
        		+ "                color: #1155cc;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-footnote {\r\n"
        		+ "                margin-top: 22px;\r\n"
        		+ "                padding: 22px 20 24px;\r\n"
        		+ "                color: #108f30;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                border: 1px solid #108f30;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #ffffff;\r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div class=\"opps\">\r\n"
        		+ "            <div class=\"opps-header\">\r\n"
        		+ "                <div class=\"opps-reminder\">Notificación de Incidencia</div>\r\n"
        		+ "                <div class=\"opps-info\">\r\n"
        		+ "                    <div class=\"opps-brand\"><img src=\"https://www.clubalpha.com.mx/images/logo_positivo2.png\" alt=\"OXXOPay\"></div>\r\n"
        		+ "                    <div class=\"opps-ammount\">\r\n"
        		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
        		+ "                        <h2>"+idCliente+"  </h2>\r\n"
        		+ "                        <p>Estimado Usuario</p>\r\n"
        		+ "                    </div>\r\n"
        		+ "                </div>\r\n"
        		+ "                <div class=\"opps-reference\">\r\n"
        		+ "                    <h3>Cliente:</h3>\r\n"
        		+ "                    <h1>"+nombre+"</h1> \r\n"
        		+ "                </div>\r\n"
        		+ "            </div>\r\n"
        		+ "            <br>\r\n"
        		+ "            <p>Le informamos que se registró la cuarta incidencia por exceder las 4 horas por ingreso, en el estacionamiento de Club Alpha 3, por lo que su Chip se encuentra desactivado definitivamente.</p>\r\n"
        		+ "            <div class=\"opps-instructions\">\r\n"
        		+ "                <small>\r\n"
        		+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes 222 235 17 35 Ext. 101\r\n"
        		+ "                    Atentamente:\r\n"
        		+ "                    Club Alpha 3\r\n"
        		+ "                    <br>\r\n"
        		+ "                    <br>\r\n"
        		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de Club Alpha, disponible en www.clubalpha.com.mx\r\n"
        		+ "                </small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom((Address)new InternetAddress(this.correoEnvia));
            mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario), new InternetAddress(this.copiaoculta) });
            mail.setSubject(asunto);
            mail.setContent(mensaje, "text/html; charset=UTF-8");
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
            transporte.close();           
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	public void enviar_correo4(String asunto,int idCliente,String nombre,String horaEntrada,String horaSalida,String texto,String cabecera) {                                         
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
 
       
        Session sesion = Session.getDefaultInstance(propiedad);	
        
       
        
        
        String mensaje = "<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
        		+ "        <title>Incidencia</title>\r\n"
        		+ "        <style type=\"text/css\">\r\n"
        		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
        		+ "            * 	 { margin: 0;padding: 0; }\r\n"
        		+ "            body { font-size: 14px; }\r\n"
        		+ "\r\n"
        		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
        		+ "\r\n"
        		+ "            h3 {\r\n"
        		+ "                margin-bottom: 10px;\r\n"
        		+ "                font-size: 15px;\r\n"
        		+ "                font-weight: 600;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps {\r\n"
        		+ "                width: 496px; \r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                box-sizing: border-box;\r\n"
        		+ "                padding: 0 45px;\r\n"
        		+ "                margin: 40px auto;\r\n"
        		+ "                overflow: hidden;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                font-family: 'Open Sans', sans-serif;\r\n"
        		+ "                color: #4f5365;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reminder {\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -1px;\r\n"
        		+ "                padding: 9px 0 10px;\r\n"
        		+ "                font-size: 11px;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                color: #ffffff;\r\n"
        		+ "                background: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info {\r\n"
        		+ "                margin-top: 26px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info:after {\r\n"
        		+ "                visibility: hidden;\r\n"
        		+ "                display: block;\r\n"
        		+ "                font-size: 0;\r\n"
        		+ "                content: \" \";\r\n"
        		+ "                clear: both;\r\n"
        		+ "                height: 0;\r\n"
        		+ "\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand {\r\n"
        		+ "                width: 45%;\r\n"
        		+ "                float: left;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand img {\r\n"
        		+ "                max-width: 150px;\r\n"
        		+ "                margin-top: 2px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount {\r\n"
        		+ "                width: 55%;\r\n"
        		+ "                float: right;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 {\r\n"
        		+ "                font-size: 36px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                line-height: 24px;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 sup {\r\n"
        		+ "                font-size: 16px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -2px\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount p {\r\n"
        		+ "                font-size: 10px;\r\n"
        		+ "                line-height: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reference {\r\n"
        		+ "                margin-top: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            h1 {\r\n"
        		+ "                font-size: 27px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                margin-top: -1px;\r\n"
        		+ "                padding: 6px 0 7px;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-instructions {\r\n"
        		+ "                margin: 32px -45px 0;\r\n"
        		+ "                padding: 32px 45px 45px;\r\n"
        		+ "                border-top: 1px solid #b0afb5;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            ol {\r\n"
        		+ "                margin: 17px 0 0 16px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            li + li {\r\n"
        		+ "                margin-top: 10px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            a {\r\n"
        		+ "                color: #1155cc;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-footnote {\r\n"
        		+ "                margin-top: 22px;\r\n"
        		+ "                padding: 22px 20 24px;\r\n"
        		+ "                color: #108f30;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                border: 1px solid #108f30;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #ffffff;\r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div class=\"opps\">\r\n"
        		+ "            <div class=\"opps-header\">\r\n"
        		+ "                <div class=\"opps-reminder\">Notificación de Incidencia</div>\r\n"
        		+ "                <div class=\"opps-info\">\r\n"
        		+ "                    <div class=\"opps-brand\"><img src=\"https://sportadvisorweb.com/wp-content/uploads/2019/09/descarga-2-2.png\" alt=\"OXXOPay\" width=\"100\" height=\"100\"></div>\r\n"
        		+ "                    <div class=\"opps-ammount\">\r\n"
        		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
        		+ "                        <h2>"+idCliente+"  </h2>\r\n"
        		+ "                        <p>Le informamos que se registró la "+cabecera+" incidencia por exceder las 4 horas por ingreso, en el estacionamiento de CIMERA.</p>\r\n"
        		+ "                        <!-- <p>Se ha detectado que ha estado mas de 4 horas en el estacionamiento</p> -->\r\n"
        		+ "                    </div>\r\n"
        		+ "                </div>\r\n"
        		+ "                <div class=\"opps-reference\">\r\n"
        		+ "                    <h3>Cliente:</h3>\r\n"
        		+ "                    <h1>"+nombre+"</h1> \r\n"
        		+ "                </div>\r\n"
        		+ "            </div>\r\n"
        		+ "            <br>\r\n"
        		+ "            <p>Fecha y hora  de entrada "+horaEntrada+" – Fecha y hora de salida: "+horaSalida+"</p>\r\n"
        		+ "            <div class=\"opps-instructions\">\r\n"
        		+ "                <h3>RECUERDE:</h3>\r\n"
        		+ "                <ol>\r\n"
        		+					texto
        		+ "                </ol>\r\n"
        		+ "                <br>\r\n"
        		+ "                <small>\r\n"
        		+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes (222) 247-30-22 / 247-85-98 Ext. 107\r\n"
        		+ "                    Atentamente:\r\n"
        		+ "                    CIMERA\r\n"
        		+ "                    <br>\r\n"
        		+ "                    <br>\r\n"
        		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de Cimera, disponible en www.cimera.com.mx\r\n"
        		+ "                </small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom((Address)new InternetAddress(this.correoEnvia));
            mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario), new InternetAddress(this.copiaoculta) });
            mail.setSubject(asunto);
            mail.setContent(mensaje, "text/html; charset=UTF-8");
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
            transporte.close();           
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	public void enviar_correo5(String asunto,int idCliente,String nombre,String horaEntrada,String horaSalida) {    
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
 
       
        Session sesion = Session.getDefaultInstance(propiedad);	
        
       
        
        
        String mensaje = "<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
        		+ "        <title>Incidencia</title>\r\n"
        		+ "        <style type=\"text/css\">\r\n"
        		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
        		+ "            * 	 { margin: 0;padding: 0; }\r\n"
        		+ "            body { font-size: 14px; }\r\n"
        		+ "\r\n"
        		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
        		+ "\r\n"
        		+ "            h3 {\r\n"
        		+ "                margin-bottom: 10px;\r\n"
        		+ "                font-size: 15px;\r\n"
        		+ "                font-weight: 600;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps {\r\n"
        		+ "                width: 496px; \r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                box-sizing: border-box;\r\n"
        		+ "                padding: 0 45px;\r\n"
        		+ "                margin: 40px auto;\r\n"
        		+ "                overflow: hidden;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                font-family: 'Open Sans', sans-serif;\r\n"
        		+ "                color: #4f5365;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reminder {\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -1px;\r\n"
        		+ "                padding: 9px 0 10px;\r\n"
        		+ "                font-size: 11px;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                color: #ffffff;\r\n"
        		+ "                background: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info {\r\n"
        		+ "                margin-top: 26px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info:after {\r\n"
        		+ "                visibility: hidden;\r\n"
        		+ "                display: block;\r\n"
        		+ "                font-size: 0;\r\n"
        		+ "                content: \" \";\r\n"
        		+ "                clear: both;\r\n"
        		+ "                height: 0;\r\n"
        		+ "\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand {\r\n"
        		+ "                width: 45%;\r\n"
        		+ "                float: left;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand img {\r\n"
        		+ "                max-width: 150px;\r\n"
        		+ "                margin-top: 2px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount {\r\n"
        		+ "                width: 55%;\r\n"
        		+ "                float: right;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 {\r\n"
        		+ "                font-size: 36px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                line-height: 24px;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 sup {\r\n"
        		+ "                font-size: 16px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -2px\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount p {\r\n"
        		+ "                font-size: 10px;\r\n"
        		+ "                line-height: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reference {\r\n"
        		+ "                margin-top: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            h1 {\r\n"
        		+ "                font-size: 27px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                margin-top: -1px;\r\n"
        		+ "                padding: 6px 0 7px;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-instructions {\r\n"
        		+ "                margin: 32px -45px 0;\r\n"
        		+ "                padding: 32px 45px 45px;\r\n"
        		+ "                border-top: 1px solid #b0afb5;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            ol {\r\n"
        		+ "                margin: 17px 0 0 16px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            li + li {\r\n"
        		+ "                margin-top: 10px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            a {\r\n"
        		+ "                color: #1155cc;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-footnote {\r\n"
        		+ "                margin-top: 22px;\r\n"
        		+ "                padding: 22px 20 24px;\r\n"
        		+ "                color: #108f30;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                border: 1px solid #108f30;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #ffffff;\r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div class=\"opps\">\r\n"
        		+ "            <div class=\"opps-header\">\r\n"
        		+ "                <div class=\"opps-reminder\">Notificación de Incidencia</div>\r\n"
        		+ "                <div class=\"opps-info\">\r\n"
        		+ "                    <div class=\"opps-brand\"><img src=\"https://sportadvisorweb.com/wp-content/uploads/2019/09/descarga-2-2.png\" alt=\"OXXOPay\" width=\"100\" height=\"100\"></div>\r\n"
        		+ "                    <div class=\"opps-ammount\">\r\n"
        		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
        		+ "                        <h2>"+idCliente+"  </h2>\r\n"
        		+ "                        <p>Estimado Usuario</p>\r\n"
        		+ "                    </div>\r\n"
        		+ "                </div>\r\n"
        		+ "                <div class=\"opps-reference\">\r\n"
        		+ "                    <h3>Cliente:</h3>\r\n"
        		+ "                    <h1>"+nombre+"</h1> \r\n"
        		+ "                </div>\r\n"
        		+ "            </div>\r\n"
        		+ "            <br>\r\n"
        		+ "            <p>Le informamos que se registró la cuarta incidencia por exceder las 4 horas por ingreso, en el estacionamiento de CIMERA, por lo que su Chip se encuentra desactivado definitivamente.</p>\r\n"
        		+ "            <div class=\"opps-instructions\">\r\n"
        		+ "                <small>\r\n"
        		+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes (222) 247-30-22 / 247-85-98 Ext. 107\r\n"
        		+ "                    Atentamente:\r\n"
        		+ "                    CIMERA\r\n"
        		+ "                    <br>\r\n"
        		+ "                    <br>\r\n"
        		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de CIMERA, disponible en www.cimera.com.mx\r\n"
        		+ "                </small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom((Address)new InternetAddress(this.correoEnvia));
            mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario), new InternetAddress(this.copiaoculta) });
            mail.setSubject(asunto);
            mail.setContent(mensaje, "text/html; charset=UTF-8");
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
            transporte.close();           
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public void enviar_correoContrasenaSports(String asunto,String idCliente,String contrasenaNueva) {    
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
 
       
        Session sesion = Session.getDefaultInstance(propiedad);	
        
       
        
        
        String mensaje = "<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
        		+ "        <title>Cambio de Contraseña</title>\r\n"
        		+ "        <style type=\"text/css\">\r\n"
        		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
        		+ "            * 	 { margin: 0;padding: 0; }\r\n"
        		+ "            body { font-size: 14px; }\r\n"
        		+ "\r\n"
        		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
        		+ "\r\n"
        		+ "            h3 {\r\n"
        		+ "                margin-bottom: 10px;\r\n"
        		+ "                font-size: 15px;\r\n"
        		+ "                font-weight: 600;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps {\r\n"
        		+ "                width: 496px; \r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                box-sizing: border-box;\r\n"
        		+ "                padding: 0 45px;\r\n"
        		+ "                margin: 40px auto;\r\n"
        		+ "                overflow: hidden;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                font-family: 'Open Sans', sans-serif;\r\n"
        		+ "                color: #4f5365;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reminder {\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -1px;\r\n"
        		+ "                padding: 9px 0 10px;\r\n"
        		+ "                font-size: 11px;\r\n"
        		+ "                text-transform: uppercase;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                color: #ffffff;\r\n"
        		+ "                background: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info {\r\n"
        		+ "                margin-top: 26px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-info:after {\r\n"
        		+ "                visibility: hidden;\r\n"
        		+ "                display: block;\r\n"
        		+ "                font-size: 0;\r\n"
        		+ "                content: \" \";\r\n"
        		+ "                clear: both;\r\n"
        		+ "                height: 0;\r\n"
        		+ "\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand {\r\n"
        		+ "                width: 45%;\r\n"
        		+ "                float: left;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-brand img {\r\n"
        		+ "                max-width: 150px;\r\n"
        		+ "                margin-top: 2px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount {\r\n"
        		+ "                width: 55%;\r\n"
        		+ "                float: right;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 {\r\n"
        		+ "                font-size: 36px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                line-height: 24px;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount h2 sup {\r\n"
        		+ "                font-size: 16px;\r\n"
        		+ "                position: relative;\r\n"
        		+ "                top: -2px\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-ammount p {\r\n"
        		+ "                font-size: 10px;\r\n"
        		+ "                line-height: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-reference {\r\n"
        		+ "                margin-top: 14px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            h1 {\r\n"
        		+ "                font-size: 27px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                margin-top: -1px;\r\n"
        		+ "                padding: 6px 0 7px;\r\n"
        		+ "                border: 1px solid #b0afb5;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-instructions {\r\n"
        		+ "                margin: 32px -45px 0;\r\n"
        		+ "                padding: 32px 45px 45px;\r\n"
        		+ "                border-top: 1px solid #b0afb5;\r\n"
        		+ "                background: #f8f9fa;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            ol {\r\n"
        		+ "                margin: 17px 0 0 16px;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            li + li {\r\n"
        		+ "                margin-top: 10px;\r\n"
        		+ "                color: #000000;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            a {\r\n"
        		+ "                color: #1155cc;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .opps-footnote {\r\n"
        		+ "                margin-top: 22px;\r\n"
        		+ "                padding: 22px 20 24px;\r\n"
        		+ "                color: #108f30;\r\n"
        		+ "                text-align: center;\r\n"
        		+ "                border: 1px solid #108f30;\r\n"
        		+ "                border-radius: 4px;\r\n"
        		+ "                background: #ffffff;\r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div class=\"opps\">\r\n"
        		+ "            <div class=\"opps-header\">\r\n"
        		+ "                <div class=\"opps-reminder\">Notificación de Cambio de Contraseña</div>\r\n"
        		+ "                <div class=\"opps-info\">\r\n"
        		+ "                    <div class=\"opps-brand\"><img src=\"https://www.todopuebla.com/companyimage/logo/thumb172/4k_logo-14089845041636749884.png\" alt=\"logo\"  width=\"100\" height=\"100\"></div>\r\n"
        		+ "                    <div class=\"opps-ammount\">\r\n"
        		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
        		+ "                        <h2>"+idCliente+"  </h2>\r\n"
        		+ "                        <p>Estimado Usuario</p>\r\n"
        		+ "                    </div>\r\n"
        		+ "                </div>\r\n"
        		+ "                <div class=\"opps-reference\">\r\n"
        		+ "                    <h3>Contraseña nueva:</h3>\r\n"
        		+ "                    <h1>"+contrasenaNueva+"</h1> \r\n"
        		+ "                </div>\r\n"
        		+ "            </div>\r\n"
        		+ "            <br>\r\n"
        		+ "            <p>Le informamos que recibimos una solicitud para cambiar su contraseña</p>\r\n"
        		+ "            <div class=\"opps-instructions\">\r\n"
        		+ "                <small>\r\n"
        		+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes (222) 395-36-54\r\n"
        		+ "                    Atentamente:\r\n"
        		+ "                    Sports Plaza\r\n"
        		+ "                    <br>\r\n"
        		+ "                    <br>\r\n"
        		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de Sports Plaza, disponible en www.sportsplaza.mx\r\n"
        		+ "                </small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom((Address)new InternetAddress(this.correoEnvia));
            mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
            mail.setSubject(asunto);
            mail.setContent(mensaje, "text/html; charset=UTF-8");
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
            transporte.close();           
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public void enviar_correoContrasenaCimera(String asunto,String idCliente,String contrasenaNueva) {    
    
    Properties propiedad = new Properties();
    propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
    propiedad.setProperty("mail.smtp.starttls.enable", "true");
    propiedad.setProperty("mail.smtp.port", "587");
    propiedad.setProperty("mail.smtp.auth", "true");
    

   
    Session sesion = Session.getDefaultInstance(propiedad);	
    
   
    
    
    String mensaje = "<html>\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
    		+ "        <title>Cambio de Contraseña</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
    		+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
    		+ "\r\n"
    		+ "            h3 {\r\n"
    		+ "                margin-bottom: 10px;\r\n"
    		+ "                font-size: 15px;\r\n"
    		+ "                font-weight: 600;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps {\r\n"
    		+ "                width: 496px; \r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                box-sizing: border-box;\r\n"
    		+ "                padding: 0 45px;\r\n"
    		+ "                margin: 40px auto;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reminder {\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -1px;\r\n"
    		+ "                padding: 9px 0 10px;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info {\r\n"
    		+ "                margin-top: 26px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info:after {\r\n"
    		+ "                visibility: hidden;\r\n"
    		+ "                display: block;\r\n"
    		+ "                font-size: 0;\r\n"
    		+ "                content: \" \";\r\n"
    		+ "                clear: both;\r\n"
    		+ "                height: 0;\r\n"
    		+ "\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand {\r\n"
    		+ "                width: 45%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand img {\r\n"
    		+ "                max-width: 150px;\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount {\r\n"
    		+ "                width: 55%;\r\n"
    		+ "                float: right;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 {\r\n"
    		+ "                font-size: 36px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                line-height: 24px;\r\n"
    		+ "                margin-bottom: 15px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 sup {\r\n"
    		+ "                font-size: 16px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -2px\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount p {\r\n"
    		+ "                font-size: 10px;\r\n"
    		+ "                line-height: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reference {\r\n"
    		+ "                margin-top: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            h1 {\r\n"
    		+ "                font-size: 27px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                margin-top: -1px;\r\n"
    		+ "                padding: 6px 0 7px;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-instructions {\r\n"
    		+ "                margin: 32px -45px 0;\r\n"
    		+ "                padding: 32px 45px 45px;\r\n"
    		+ "                border-top: 1px solid #b0afb5;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            ol {\r\n"
    		+ "                margin: 17px 0 0 16px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            li + li {\r\n"
    		+ "                margin-top: 10px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            a {\r\n"
    		+ "                color: #1155cc;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-footnote {\r\n"
    		+ "                margin-top: 22px;\r\n"
    		+ "                padding: 22px 20 24px;\r\n"
    		+ "                color: #108f30;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                border: 1px solid #108f30;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #ffffff;\r\n"
    		+ "            }\r\n"
    		+ "        </style>\r\n"
    		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
    		+ "    </head>\r\n"
    		+ "    <body>\r\n"
    		+ "        <div class=\"opps\">\r\n"
    		+ "            <div class=\"opps-header\">\r\n"
    		+ "                <div class=\"opps-reminder\">Notificación de Cambio de Contraseña</div>\r\n"
    		+ "                <div class=\"opps-info\">\r\n"
    		+ "                    <div class=\"opps-brand\"><img src=\"https://sportadvisorweb.com/wp-content/uploads/2019/09/descarga-2-2.png\" alt=\"logo\" width=\"75\" height=\"75\"></div>\r\n"
    		+ "                    <div class=\"opps-ammount\">\r\n"
    		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
    		+ "                        <h2>"+idCliente+" </h2>\r\n"
    		+ "                        <p>Estimado Usuario</p>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                <div class=\"opps-reference\">\r\n"
    		+ "                    <h3>Contraseña nueva:</h3>\r\n"
    		+ "                    <h1>"+contrasenaNueva+"</h1> \r\n"
    		+ "                </div>\r\n"
    		+ "            </div>\r\n"
    		+ "            <br>\r\n"
    		+ "            <p>Le informamos que recibimos una solicitud para cambiar su contraseña</p>\r\n"
    		+ "            <div class=\"opps-instructions\">\r\n"
    		+ "                <small>\r\n"
    		+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes (222) 247-30-22 / 247-85-98 Ext. 107\r\n"
    		+ "                    Atentamente:\r\n"
    		+ "                    CIMERA\r\n"
    		+ "                    <br>\r\n"
    		+ "                    <br>\r\n"
    		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de CIMERA, disponible en www.cimera.com.mx\r\n"
    		+ "                </small>\r\n"
    		+ "            </div>\r\n"
    		+ "        </div>\r\n"
    		+ "    </body>\r\n"
    		+ "</html>";
    MimeMessage mail = new MimeMessage(sesion);
    
    try {
        mail.setFrom((Address)new InternetAddress(this.correoEnvia));
        mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
        mail.setSubject(asunto);
        mail.setContent(mensaje, "text/html; charset=UTF-8");
        
        Transport transporte = sesion.getTransport("smtp");
        transporte.connect(correoEnvia,contrasena);
        transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
        transporte.close();           
    } catch (AddressException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MessagingException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void enviar_correoContrasenaAlpha(String asunto,String idCliente,String contrasenaNueva) {    
    
    Properties propiedad = new Properties();
    propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
    propiedad.setProperty("mail.smtp.starttls.enable", "true");
    propiedad.setProperty("mail.smtp.port", "587");
    propiedad.setProperty("mail.smtp.auth", "true");
    

   
    Session sesion = Session.getDefaultInstance(propiedad);	
    
   
    
    
    String mensaje = "<html>\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n"
    		+ "        <title>Cambio de Contraseña</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
    		+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
    		+ "\r\n"
    		+ "            h3 {\r\n"
    		+ "                margin-bottom: 10px;\r\n"
    		+ "                font-size: 15px;\r\n"
    		+ "                font-weight: 600;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps {\r\n"
    		+ "                width: 496px; \r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                box-sizing: border-box;\r\n"
    		+ "                padding: 0 45px;\r\n"
    		+ "                margin: 40px auto;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reminder {\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -1px;\r\n"
    		+ "                padding: 9px 0 10px;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info {\r\n"
    		+ "                margin-top: 26px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info:after {\r\n"
    		+ "                visibility: hidden;\r\n"
    		+ "                display: block;\r\n"
    		+ "                font-size: 0;\r\n"
    		+ "                content: \" \";\r\n"
    		+ "                clear: both;\r\n"
    		+ "                height: 0;\r\n"
    		+ "\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand {\r\n"
    		+ "                width: 45%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand img {\r\n"
    		+ "                max-width: 150px;\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount {\r\n"
    		+ "                width: 55%;\r\n"
    		+ "                float: right;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 {\r\n"
    		+ "                font-size: 36px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                line-height: 24px;\r\n"
    		+ "                margin-bottom: 15px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 sup {\r\n"
    		+ "                font-size: 16px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -2px\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount p {\r\n"
    		+ "                font-size: 10px;\r\n"
    		+ "                line-height: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reference {\r\n"
    		+ "                margin-top: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            h1 {\r\n"
    		+ "                font-size: 27px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                margin-top: -1px;\r\n"
    		+ "                padding: 6px 0 7px;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-instructions {\r\n"
    		+ "                margin: 32px -45px 0;\r\n"
    		+ "                padding: 32px 45px 45px;\r\n"
    		+ "                border-top: 1px solid #b0afb5;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            ol {\r\n"
    		+ "                margin: 17px 0 0 16px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            li + li {\r\n"
    		+ "                margin-top: 10px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            a {\r\n"
    		+ "                color: #1155cc;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-footnote {\r\n"
    		+ "                margin-top: 22px;\r\n"
    		+ "                padding: 22px 20 24px;\r\n"
    		+ "                color: #108f30;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                border: 1px solid #108f30;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #ffffff;\r\n"
    		+ "            }\r\n"
    		+ "        </style>\r\n"
    		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\">\r\n"
    		+ "    </head>\r\n"
    		+ "    <body>\r\n"
    		+ "        <div class=\"opps\">\r\n"
    		+ "            <div class=\"opps-header\">\r\n"
    		+ "                <div class=\"opps-reminder\">Notificación de Cambio de Contraseña</div>\r\n"
    		+ "                <div class=\"opps-info\">\r\n"
    		+ "                    <div class=\"opps-brand\"><img src=\"https://www.clubalpha.com.mx/images/logo_positivo2.png\" alt=\"logo\" width=\"125\" height=\"100\"></div>\r\n"
    		+ "                    <div class=\"opps-ammount\">\r\n"
    		+ "                        <h3>¡Excelente Dia!</h3>\r\n"
    		+ "                        <h2>"+idCliente+"  </h2>\r\n"
    		+ "                        <p>Estimado Usuario</p>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                <div class=\"opps-reference\">\r\n"
    		+ "                    <h3>Contraseña nueva:</h3>\r\n"
    		+ "                    <h1>"+contrasenaNueva+"</h1> \r\n"
    		+ "                </div>\r\n"
    		+ "            </div>\r\n"
    		+ "            <br>\r\n"
    		+ "            <p>Le informamos que recibimos una solicitud para cambiar su contraseña</p>\r\n"
    		+ "            <div class=\"opps-instructions\">\r\n"
    		+ "                <small>\r\n"
    		+ "                    Atentamente:\r\n"
    		+ "                    Club Alpha\r\n"
    		+ "                    <br>\r\n"
    		+ "                    <br>\r\n"
    		+ "                    Los datos aquí contenidos son tratados en apego al “Aviso de Privacidad” de Club Alpha, disponible en www.clubalpha.com.mx\r\n"
    		+ "                </small>\r\n"
    		+ "            </div>\r\n"
    		+ "        </div>\r\n"
    		+ "    </body>\r\n"
    		+ "</html>";
    MimeMessage mail = new MimeMessage(sesion);
    
    try {
        mail.setFrom((Address)new InternetAddress(this.correoEnvia));
        mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
        mail.setSubject(asunto);
        mail.setContent(mensaje, "text/html; charset=UTF-8");
        
        Transport transporte = sesion.getTransport("smtp");
        transporte.connect(correoEnvia,contrasena);
        transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
        transporte.close();           
    } catch (AddressException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MessagingException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void enviar_rutina(String asunto,String idCliente,String fotoCliente, String nombreCliente, String club, String inicio,
		String fin, String grupoMuscular, String segmento, String listaEjercicios,String lista2,String comentarios) {    
    
    Properties propiedad = new Properties();
    propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
    propiedad.setProperty("mail.smtp.starttls.enable", "true");
    propiedad.setProperty("mail.smtp.port", "587");
    propiedad.setProperty("mail.smtp.auth", "true");
    

   
    Session sesion = Session.getDefaultInstance(propiedad);	
    
   
    
    
    String mensaje = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n"
    		+ "        <title>Rutina Entrenamiento</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
    		+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
    		+ "\r\n"
    		+ "            h3 {\r\n"
    		+ "                margin-bottom: 5px;\r\n"
    		+ "                font-size: 15px;\r\n"
    		+ "                font-weight: 600;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps {\r\n"
    		+ "                width: 700px; \r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                box-sizing: border-box;\r\n"
    		+ "                padding: 0 45px;\r\n"
    		+ "                margin: 40px auto;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "				.card {\r\n"
    		+ "                width: 100%; \r\n"
    		+ "                height: 140px;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                padding: 2px 0px;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "            .card img{\r\n"
    		+ "                float: left;\r\n"
    		+ "            } \r\n"
    		+ "            .card table{\r\n"
    		+ "                float: right;\r\n"
    		+ "            } "
    		+ "\r\n"
    		+ "            .opps-reminder {\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -1px;\r\n"
    		+ "                padding: 9px 0 10px;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info {\r\n"
    		+ "                margin-top: 26px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info:after {\r\n"
    		+ "                visibility: hidden;\r\n"
    		+ "                display: block;\r\n"
    		+ "                font-size: 0;\r\n"
    		+ "                content: \" \";\r\n"
    		+ "                clear: both;\r\n"
    		+ "                height: 0;\r\n"
    		+ "\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand {\r\n"
    		+ "                width: 45%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand img {\r\n"
    		+ "                max-width: 150px;\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount {\r\n"
    		+ "                width: 78%;\r\n"
    		+ "                float: right;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 {\r\n"
    		+ "                font-size: 10PX;\r\n"
    		+ "                color: rgb(0, 0, 0);\r\n"
    		+ "                line-height: 24px;\r\n"
    		+ "                margin-bottom: 72px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 sup {\r\n"
    		+ "                font-size: 16px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -2px\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount p {\r\n"
    		+ "                font-size: 10px;\r\n"
    		+ "                line-height: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reference {\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "                margin-bottom: 5px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            h1 {\r\n"
    		+ "                font-size: 20px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                margin-top: -1px;\r\n"
    		+ "                padding: 6px 0 7px;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-instructions {\r\n"
    		+ "                margin: 32px -45px 0;\r\n"
    		+ "                padding: 32px 45px 45px;\r\n"
    		+ "                border-top: 1px solid #b0afb5;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            ol {\r\n"
    		+ "                margin: 17px 0 0 16px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            li + li {\r\n"
    		+ "                margin-top: 10px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            a {\r\n"
    		+ "                color: #1155cc;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-footnote {\r\n"
    		+ "                margin-top: 22px;\r\n"
    		+ "                padding: 22px 20px 24px;\r\n"
    		+ "                color: #108f30;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                border: 1px solid #108f30;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #ffffff;\r\n"
    		+ "            }\r\n"
    		+ "            img {\r\n"
    		+ "                border-radius: 8px;\r\n"
    		+ "            }\r\n"
    		+ "            table{\r\n"
    		+ "                background-color: #e0e0e0;\r\n"
    		+ "                width: 100%;\r\n"
    		+ "            }\r\n"
    		+ "            td{\r\n"
    		+ "                color: #636363;\r\n"
    		+ "                text-align: left;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "            }\r\n"
    		+ "            th{\r\n"
    		+ "                text-align: left;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "            }\r\n"
    		+ "            h4{\r\n"
    		+ "                \r\n"
    		+ "                font-size: 25px;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: rgb(224, 26, 26);\r\n"
    		+ "            }\r\n"
    		+ "            h5{\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                font-size: 12px;\r\n"
    		+ "                background-color: rgb(224, 26, 26);  \r\n"
    		+ "                \r\n"
    		+ "                width: 10%;\r\n"
    		+ "                float: left;      \r\n"
    		+ "            }\r\n"
    		+ "            h6{\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                font-size: 12px;\r\n"
    		+ "                background-color: rgb(0, 0, 0);\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                width: 90%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "            .card-grid{\r\n"
    		+ "					width: 100%; \r\n"
    		+ "                column-count: 1;\r\n"
    		+ "            }\r\n"
    		+ "        </style>\r\n"
    		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\"/>\r\n"
    		+ "    </head>\r\n"
    		+ "    <body>\r\n"
    		+ "        <div class=\"opps\">\r\n"
    		+ "            <div class=\"opps-header\">\r\n"
    		+ "                <div class=\"opps-info\">\r\n"
    		+ "                    <img style=\"width: 18%; height: 16%;\" src=\"data:image/png;base64,"+fotoCliente+"\"/>\r\n"
    		+ "                    <div class=\"opps-ammount\">\r\n"
    		+ "                        <h3>HOLA, "+nombreCliente+" "+idCliente+"</h3>\r\n"
    		+ "                        <h2>"+club.toUpperCase()+" TE PRESENTA TU ENTRENAMIENTO PERSONALIZADO.</h2>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <thead>\r\n"
    		+ "                              <tr align=\"center\">\r\n"
    		+ "                                <th scope=\"col\">Inicio</th>\r\n"
    		+ "                                <th scope=\"col\">Fin</th>\r\n"
    		+ "                                <th scope=\"col\">Objetivo</th>\r\n"
    		+ "                              </tr>\r\n"
    		+ "                            </thead>\r\n"
    		+ "                            <tbody>\r\n"
    		+ "                              <tr>\r\n"
    		+ "                                <td scope=\"row\">"+inicio+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+fin+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+grupoMuscular+"</td>\r\n"
    		+ "                              </tr>\r\n"
    		+ "                            </tbody>\r\n"
    		+ "                          </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "            </div>\r\n"
    		+ "            <div align=\"center\">\r\n"
    		+ "                <h4>"+segmento+"</h4>\r\n"
    		+ "            </div>\r\n"
    		+ "            <div align=\"center\">\r\n"
    		+ "                <h4>"+comentarios+"</h4>\r\n"
    		+ "            </div>\r\n"
    		+ "            <div class='card-grid'>\r\n"
    								+listaEjercicios
    		+ "            </div>\r\n"
    		+ "        </div>\r\n"
    		+ "    </body>\r\n"
    		+ "</html>";
    String mensaje2 = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n"
    		+ "        <title>Rutina Entrenamiento</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
    		+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
    		+ "\r\n"
    		+ "            h3 {\r\n"
    		+ "                margin-bottom: 5px;\r\n"
    		+ "                font-size: 15px;\r\n"
    		+ "                font-weight: 600;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps {\r\n"
    		+ "                width: 700px; \r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                box-sizing: border-box;\r\n"
    		+ "                padding: 0 45px;\r\n"
    		+ "                margin: 40px auto;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "				.card {\r\n"
    		+ "                width: 100%; \r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                padding: 2px 0px;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "            .card img{\r\n"
    		+ "                float: left;\r\n"
    		+ "            } \r\n"
    		+ "            .card table{\r\n"
    		+ "                float: right;\r\n"
    		+ "            } "
    		+ "\r\n"
    		+ "            .opps-reminder {\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -1px;\r\n"
    		+ "                padding: 9px 0 10px;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info {\r\n"
    		+ "                margin-top: 26px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info:after {\r\n"
    		+ "                visibility: hidden;\r\n"
    		+ "                display: block;\r\n"
    		+ "                font-size: 0;\r\n"
    		+ "                content: \" \";\r\n"
    		+ "                clear: both;\r\n"
    		+ "                height: 0;\r\n"
    		+ "\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand {\r\n"
    		+ "                width: 45%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand img {\r\n"
    		+ "                max-width: 150px;\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount {\r\n"
    		+ "                width: 78%;\r\n"
    		+ "                float: right;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 {\r\n"
    		+ "                font-size: 10PX;\r\n"
    		+ "                color: rgb(0, 0, 0);\r\n"
    		+ "                line-height: 24px;\r\n"
    		+ "                margin-bottom: 72px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 sup {\r\n"
    		+ "                font-size: 16px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -2px\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount p {\r\n"
    		+ "                font-size: 10px;\r\n"
    		+ "                line-height: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reference {\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "                margin-bottom: 5px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            h1 {\r\n"
    		+ "                font-size: 20px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                margin-top: -1px;\r\n"
    		+ "                padding: 6px 0 7px;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-instructions {\r\n"
    		+ "                margin: 32px -45px 0;\r\n"
    		+ "                padding: 32px 45px 45px;\r\n"
    		+ "                border-top: 1px solid #b0afb5;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            ol {\r\n"
    		+ "                margin: 17px 0 0 16px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            li + li {\r\n"
    		+ "                margin-top: 10px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            a {\r\n"
    		+ "                color: #1155cc;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-footnote {\r\n"
    		+ "                margin-top: 22px;\r\n"
    		+ "                padding: 22px 20px 24px;\r\n"
    		+ "                color: #108f30;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                border: 1px solid #108f30;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #ffffff;\r\n"
    		+ "            }\r\n"
    		+ "            img {\r\n"
    		+ "                border-radius: 8px;\r\n"
    		+ "            }\r\n"
    		+ "            table{\r\n"
    		+ "                background-color: #e0e0e0;\r\n"
    		+ "                width: 100%;\r\n"
    		+ "            }\r\n"
    		+ "            td{\r\n"
    		+ "                color: #636363;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "            }\r\n"
    		+ "            th{\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "            }\r\n"
    		+ "            h4{\r\n"
    		+ "                \r\n"
    		+ "                font-size: 25px;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: rgb(224, 26, 26);\r\n"
    		+ "            }\r\n"
    		+ "            h5{\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                font-size: 12px;\r\n"
    		+ "                background-color: rgb(224, 26, 26);  \r\n"
    		+ "                \r\n"
    		+ "                width: 4%;\r\n"
    		+ "                float: left;      \r\n"
    		+ "            }\r\n"
    		+ "            h6{\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                font-size: 12px;\r\n"
    		+ "                background-color: rgb(0, 0, 0);\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                width: 96%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "            .card-grid{\r\n"
    		+ "					width: 100%; \r\n"
    		+ "                column-count: 2;\r\n"
    		+ "            }\r\n"
    		+ "        </style>\r\n"
    		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\"/>\r\n"
    		+ "    </head>\r\n"
    		+ "    <body>\r\n"
    		+ "        <div class=\"opps\">\r\n"
    		+ "            <div class=\"opps-header\">\r\n"
    		+ "                <div class=\"opps-info\">\r\n"
    		+ "                    <img style=\"width: 18%; height: 10%;\" src=\"data:image/png;base64,"+fotoCliente+"\"/>\r\n"
    		+ "                    <div class=\"opps-ammount\">\r\n"
    		+ "                        <h3>HOLA, "+nombreCliente+" "+idCliente+"</h3>\r\n"
    		+ "                        <h2>"+club.toUpperCase()+" TE PRESENTA TU ENTRENAMIENTO PERSONALIZADO.</h2>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <thead>\r\n"
    		+ "                              <tr align=\"center\">\r\n"
    		+ "                                <th scope=\"col\">Inicio</th>\r\n"
    		+ "                                <th scope=\"col\">Fin</th>\r\n"
    		+ "                                <th scope=\"col\">Objetivo</th>\r\n"
    		+ "                              </tr>\r\n"
    		+ "                            </thead>\r\n"
    		+ "                            <tbody>\r\n"
    		+ "                              <tr>\r\n"
    		+ "                                <td scope=\"row\">"+inicio+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+fin+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+grupoMuscular+"</td>\r\n"
    		+ "                              </tr>\r\n"
    		+ "                            </tbody>\r\n"
    		+ "                          </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "            </div>\r\n"
    		+ "            <div align=\"center\">\r\n"
    		+ "                <h4>"+segmento+"</h4>\r\n"
    		+ "            </div>\r\n"
    		+ "            <div class='card-grid'>\r\n"
    								+lista2
    		+ "            </div>\r\n"
    		+ "        </div>\r\n"
    		+ "    </body>\r\n"
    		+ "</html>";
    String ruta = "prueba.html";
    String ficheroPDF = "plantilla.pdf"; 
    try {        
        File file = new File(ruta);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(mensaje);
        bw.close();
        /*String url = new File(ruta).toURI().toURL().toString(); 
        OutputStream os = new FileOutputStream(ficheroPDF);  
        ITextRenderer renderer = new ITextRenderer();     
        renderer.setDocument(url); 
        renderer.layout(); 
        renderer.createPDF(os); 
        os.close(); */
        Document document = Jsoup.parse(file, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try (OutputStream os = new FileOutputStream(ficheroPDF)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(ficheroPDF);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
            builder.run();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    /*String html = "plantilla.html";
    try {        
        File file = new File(html);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(mensaje2);
        bw.close();
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    BodyPart contenido=new MimeBodyPart();
    BodyPart contenido2=new MimeBodyPart();
    String html="";
    if(club.equals("Club Alpha 2")|| club.equals("Club Alpha 3")) {
		html="rutina_alpha.html";
	}else if(club.equals("CIMERA")) {
		html="rutina_alpha.html";
	}else {
		html="rutina_alpha.html";
	}
    String texto="";
    File doc =
            new File(html);
          Scanner obj;
		try {
			obj = new Scanner(doc);

	          while (obj.hasNextLine())
	        	  texto=texto+obj.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    try {
    	contenido.setDataHandler(new DataHandler(new FileDataSource(ficheroPDF)));
    	contenido.setFileName(ficheroPDF);
    	contenido2.setContent(texto, "text/html; charset=UTF-8");
	} catch (MessagingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    MimeMultipart m =new MimeMultipart();
    try {
		m.addBodyPart(contenido2);
		m.addBodyPart(contenido);
	} catch (MessagingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
    MimeMessage mail = new MimeMessage(sesion);
   
    /*String ficheroHTML = "prueba.html"; 
    String url = new File(ficheroHTML).toURI().toURL().toString(); 
    String ficheroPDF = "plantilla.pdf"; 
    OutputStream os = new FileOutputStream(ficheroPDF);  
    ITextRenderer renderer = new ITextRenderer();     
    renderer.setDocument(url); 
    renderer.layout(); 
    renderer.createPDF(os); 
    os.close(); */
    try {
        mail.setFrom((Address)new InternetAddress(this.correoEnvia));
        mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
        mail.setSubject(asunto);
        mail.setContent(m, "text/html; charset=UTF-8");
        
        Transport transporte = sesion.getTransport("smtp");
        transporte.connect(correoEnvia,contrasena);
        transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
        transporte.close();           
    } catch (AddressException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MessagingException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void enviar_pesaje(float liquidosCorporales, float masaOsea, float adiposidadVisceral, float masaMuscular, float grasaCorporal, 
		float pesoCorporal, float calorias, float metabolismoBasal, int edadMetabolica, float imc, String fecha, int idCliente, String nombre,
		String fotoCliente, String club, String asunto) {    
    
    Properties propiedad = new Properties();
    propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
    propiedad.setProperty("mail.smtp.starttls.enable", "true");
    propiedad.setProperty("mail.smtp.port", "587");
    propiedad.setProperty("mail.smtp.auth", "true");
    

   
    Session sesion = Session.getDefaultInstance(propiedad);	
    
   
    
    
    String mensaje = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n"
    		+ "        <title>Rutina Entrenamiento</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- */\r\n"
    		+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- */\r\n"
    		+ "\r\n"
    		+ "            h3 {\r\n"
    		+ "                margin-bottom: 5px;\r\n"
    		+ "                font-size: 15px;\r\n"
    		+ "                font-weight: 600;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps {\r\n"
    		+ "                width: 700px; \r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                box-sizing: border-box;\r\n"
    		+ "                padding: 0 45px;\r\n"
    		+ "                margin: 40px auto;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "				.card {\r\n"
    		+ "                width: 100%; \r\n"
    		+ "                height: 125px;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                padding: 2px 0px;\r\n"
    		+ "                overflow: hidden;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                font-family: 'Open Sans', sans-serif;\r\n"
    		+ "                color: #4f5365;\r\n"
    		+ "            }\r\n"
    		+ "            .card img{\r\n"
    		+ "                float: left;\r\n"
    		+ "            } \r\n"
    		+ "            .card table{\r\n"
    		+ "                float: right;\r\n"
    		+ "            } \r\n"
    		+ "            .opps-reminder {\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -1px;\r\n"
    		+ "                padding: 9px 0 10px;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "                text-transform: uppercase;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info {\r\n"
    		+ "                margin-top: 26px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-info:after {\r\n"
    		+ "                visibility: hidden;\r\n"
    		+ "                display: block;\r\n"
    		+ "                font-size: 0;\r\n"
    		+ "                content: \" \";\r\n"
    		+ "                clear: both;\r\n"
    		+ "                height: 0;\r\n"
    		+ "\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand {\r\n"
    		+ "                width: 45%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-brand img {\r\n"
    		+ "                max-width: 150px;\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount {\r\n"
    		+ "                width: 78%;\r\n"
    		+ "                float: right;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 {\r\n"
    		+ "                font-size: 15PX;\r\n"
    		+ "                color: rgb(252, 0, 0);\r\n"
    		+ "                line-height: 24px;\r\n"
    		+ "                margin-bottom: 72px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount h2 sup {\r\n"
    		+ "                font-size: 16px;\r\n"
    		+ "                position: relative;\r\n"
    		+ "                top: -2px\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-ammount p {\r\n"
    		+ "                font-size: 10px;\r\n"
    		+ "                line-height: 14px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-reference {\r\n"
    		+ "                margin-top: 2px;\r\n"
    		+ "                margin-bottom: 5px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            h1 {\r\n"
    		+ "                font-size: 20px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                margin-top: -1px;\r\n"
    		+ "                padding: 6px 0 7px;\r\n"
    		+ "                border: 1px solid #b0afb5;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-instructions {\r\n"
    		+ "                margin: 32px -45px 0;\r\n"
    		+ "                padding: 32px 45px 45px;\r\n"
    		+ "                border-top: 1px solid #b0afb5;\r\n"
    		+ "                background: #f8f9fa;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            ol {\r\n"
    		+ "                margin: 17px 0 0 16px;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            li + li {\r\n"
    		+ "                margin-top: 10px;\r\n"
    		+ "                color: #000000;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            a {\r\n"
    		+ "                color: #1155cc;\r\n"
    		+ "            }\r\n"
    		+ "\r\n"
    		+ "            .opps-footnote {\r\n"
    		+ "                margin-top: 22px;\r\n"
    		+ "                padding: 22px 20px 24px;\r\n"
    		+ "                color: #108f30;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                border: 1px solid #108f30;\r\n"
    		+ "                border-radius: 4px;\r\n"
    		+ "                background: #ffffff;\r\n"
    		+ "            }\r\n"
    		+ "            img {\r\n"
    		+ "                border-radius: 8px;\r\n"
    		+ "            }\r\n"
    		+ "            table{\r\n"
    		+ "                background-color: #e0e0e0;\r\n"
    		+ "                width: 100%;\r\n"
    		+ "            }\r\n"
    		+ "            td{\r\n"
    		+ "                color: #636363;\r\n"
    		+ "                text-align: left;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "            }\r\n"
    		+ "            th{\r\n"
    		+ "                text-align: left;\r\n"
    		+ "                font-size: 11px;\r\n"
    		+ "            }\r\n"
    		+ "            h4{\r\n"
    		+ "                \r\n"
    		+ "                font-size: 25px;\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                color: rgb(224, 26, 26);\r\n"
    		+ "            }\r\n"
    		+ "            h5{\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                font-size: 12px;\r\n"
    		+ "                background-color: rgb(224, 26, 26);  \r\n"
    		+ "                \r\n"
    		+ "                width: 4%;\r\n"
    		+ "                float: left;      \r\n"
    		+ "            }\r\n"
    		+ "            h6{\r\n"
    		+ "                color: #ffffff;\r\n"
    		+ "                font-size: 12px;\r\n"
    		+ "                background-color: rgb(0, 0, 0);\r\n"
    		+ "                text-align: center;\r\n"
    		+ "                width: 100%;\r\n"
    		+ "                float: left;\r\n"
    		+ "            }\r\n"
    		+ "            .card-grid{\r\n"
    		+ "					width: 100%; \r\n"
    		+ "                column-count: 1;\r\n"
    		+ "            }\r\n"
    		+ "        </style>\r\n"
    		+ "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\"/>\r\n"
    		+ "    </head>\r\n"
    		+ "    <body>\r\n"
    		+ "        <div class=\"opps\">\r\n"
    		+ "            <div class=\"opps-header\">\r\n"
    		+ "                <div class=\"opps-info\">\r\n"
    		+ "                    <img style=\"width: 18%; height: 16%;\" src=\"data:image/png;base64,"+fotoCliente+"\"/>\r\n"
    		+ "                    <div class=\"opps-ammount\">\r\n"
    		+ "                        <h3>HOLA, "+nombre+" "+idCliente+"</h3>\r\n"
    		+ "                        <h2>"+fecha+"</h2>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <thead>\r\n"
    		+ "                              <tr align=\"center\">\r\n"
    		+ "                                <th scope=\"col\">I.M.C.</th>\r\n"
    		+ "                                <th scope=\"col\">Edad Metabólica</th>\r\n"
    		+ "                                <th scope=\"col\">Metabolismo Basal</th>\r\n"
    		+ "                                <th scope=\"col\">Aporte cálorico diario</th>\r\n"
    		+ "                              </tr>\r\n"
    		+ "                            </thead>\r\n"
    		+ "                            <tbody>\r\n"
    		+ "                              <tr>\r\n"
    		+ "                                <td scope=\"row\">"+imc+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+edadMetabolica+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+metabolismoBasal+"</td>\r\n"
    		+ "                                <td scope=\"row\">"+calorias+"</td>\r\n"
    		+ "                              </tr>\r\n"
    		+ "                            </tbody>\r\n"
    		+ "                          </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "            </div>\r\n"
    		+ "            <div class='card-grid'>\r\n"
    		+ "                <div class=\"card\">\r\n"
    		+ "                    <div>\r\n"
    		+ "                         <h6>Peso Corporal</h6>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <tr>\r\n"
    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
    		+ "                                    <img width=\"125\" height=\"125\" src=\"https://cdn-icons-png.flaticon.com/512/822/822133.png\"/>\r\n"
    		+ "                                </td>\r\n"
    		+ "                                <td style=\" border-right: 0\">\r\n"
    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">"+pesoCorporal+" KG</h3>\r\n"
    		+ "                                    \r\n"
    		+ "                                </td>\r\n"
    		+ "                            </tr>\r\n"
    		+ "                        </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                <div class=\"card\">\r\n"
    		+ "                    <div>\r\n"
    		+ "                         <h6>Grasa Corporal</h6>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <tr>\r\n"
    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"https://cdn-icons-png.flaticon.com/512/425/425820.png\"/>\r\n"
    		+ "                                </td>\r\n"
    		+ "                                <td style=\" border-right: 0\">\r\n"
    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">"+grasaCorporal+" %</h3>\r\n"
    		+ "                                    \r\n"
    		+ "                                </td>\r\n"
    		+ "                            </tr>\r\n"
    		+ "                        </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                <div class=\"card\">\r\n"
    		+ "                    <div>\r\n"
    		+ "                         <h6>Masa Muscular</h6>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <tr>\r\n"
    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"https://cdn-icons-png.flaticon.com/512/2690/2690150.png\"/>\r\n"
    		+ "                                </td>\r\n"
    		+ "                                <td style=\" border-right: 0\">\r\n"
    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">"+masaMuscular+" KG</h3>\r\n"
    		+ "                                    \r\n"
    		+ "                                </td>\r\n"
    		+ "                            </tr>\r\n"
    		+ "                        </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                \r\n"
    		+ "                <div class=\"card\">\r\n"
    		+ "                    <div>\r\n"
    		+ "                         <h6>Adiposidad Visceral</h6>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <tr>\r\n"
    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"grasas.png\"/>\r\n"
    		+ "                                </td>\r\n"
    		+ "                                <td style=\" border-right: 0\">\r\n"
    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">Nivel de Grasa: "+adiposidadVisceral+"</h3>\r\n"
    		+ "                                    \r\n"
    		+ "                                </td>\r\n"
    		+ "                            </tr>\r\n"
    		+ "                        </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                <div class=\"card\">\r\n"
    		+ "                    <div>\r\n"
    		+ "                         <h6>Masa Ósea</h6>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <tr>\r\n"
    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"https://cdn-icons-png.flaticon.com/512/2861/2861090.png\"/>\r\n"
    		+ "                                </td>\r\n"
    		+ "                                <td style=\" border-right: 0\">\r\n"
    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">"+masaOsea+" KG</h3>\r\n"
    		+ "                                    \r\n"
    		+ "                                </td>\r\n"
    		+ "                            </tr>\r\n"
    		+ "                        </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "                <div class=\"card\">\r\n"
    		+ "                    <div>\r\n"
    		+ "                         <h6>Liquidos Corporales</h6>\r\n"
    		+ "                        <table>\r\n"
    		+ "                            <tr>\r\n"
    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"https://cdn-icons-png.flaticon.com/512/1694/1694412.png\"/>\r\n"
    		+ "                                </td>\r\n"
    		+ "                                <td style=\" border-right: 0\">\r\n"
    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">"+liquidosCorporales+"%</h3>\r\n"
    		+ "                                    \r\n"
    		+ "                                </td>\r\n"
    		+ "                            </tr>\r\n"
    		+ "                        </table>\r\n"
    		+ "                    </div>\r\n"
    		+ "                </div>\r\n"
    		+ "            </div>\r\n"
    		+ "        </div>\r\n"
    		+ "    </body>\r\n"
    		+ "</html>";
    
    String ruta = "prueba.html";
    String ficheroPDF = "plantilla.pdf"; 
    try {        
        File file = new File(ruta);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(mensaje);
        bw.close();
        /*String url = new File(ruta).toURI().toURL().toString(); 
        OutputStream os = new FileOutputStream(ficheroPDF);  
        ITextRenderer renderer = new ITextRenderer();     
        renderer.setDocument(url); 
        renderer.layout(); 
        renderer.createPDF(os); 
        os.close(); */
        Document document = Jsoup.parse(file, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try (OutputStream os = new FileOutputStream(ficheroPDF)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(ficheroPDF);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
            builder.run();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    /*String html = "plantilla.html";
    try {        
        File file = new File(html);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(mensaje2);
        bw.close();
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    BodyPart contenido=new MimeBodyPart();
    BodyPart contenido2=new MimeBodyPart();
    String html="";
    if(club.equals("Club Alpha 2")|| club.equals("Club Alpha 3")) {
		html="peso_alpha.html";
	}else if(club.equals("CIMERA")) {
		html="peso_alpha.html";
	}else {
		html="peso_alpha.html";
	}
    String texto="";
    File doc =
            new File(html);
          Scanner obj;
		try {
			obj = new Scanner(doc);

	          while (obj.hasNextLine())
	        	  texto=texto+obj.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    try {
    	contenido.setDataHandler(new DataHandler(new FileDataSource(ficheroPDF)));
    	contenido.setFileName(ficheroPDF);
    	contenido2.setContent(texto, "text/html; charset=UTF-8");
	} catch (MessagingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    MimeMultipart m =new MimeMultipart();
    try {
		m.addBodyPart(contenido2);
		m.addBodyPart(contenido);
	} catch (MessagingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
    MimeMessage mail = new MimeMessage(sesion);
   
    /*String ficheroHTML = "prueba.html"; 
    String url = new File(ficheroHTML).toURI().toURL().toString(); 
    String ficheroPDF = "plantilla.pdf"; 
    OutputStream os = new FileOutputStream(ficheroPDF);  
    ITextRenderer renderer = new ITextRenderer();     
    renderer.setDocument(url); 
    renderer.layout(); 
    renderer.createPDF(os); 
    os.close(); */
    try {
        mail.setFrom((Address)new InternetAddress(this.correoEnvia));
        mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
        mail.setSubject(asunto);
        mail.setContent(m, "text/html; charset=UTF-8");
        
        Transport transporte = sesion.getTransport("smtp");
        transporte.connect(correoEnvia,contrasena);
        transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
        transporte.close();           
    } catch (AddressException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MessagingException ex) {
        Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
    }
}

}