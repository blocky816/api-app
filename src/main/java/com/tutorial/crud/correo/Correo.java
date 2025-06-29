package com.tutorial.crud.correo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.nio.charset.Charset;
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

	public void enviar_correo6(String asunto,int idCliente,String nombre,String horaEntrada,String horaSalida,String texto,String cabecera) {
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
				+ "                        <p>Le informamos que se registró la "+cabecera+" incidencia por exceder las 4 horas por ingreso, en el estacionamiento de Club Alpha 2.</p>\r\n"
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
				+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes 222 243 10 34 Ext. 127\r\n"
				+ "                    Atentamente:\r\n"
				+ "                    Club Alpha 2\r\n"
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

	public void enviar_correo7(String asunto,int idCliente,String nombre,String horaEntrada,String horaSalida) {

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
				+ "            <p>Le informamos que se registró la cuarta incidencia por exceder las 4 horas por ingreso, en el estacionamiento de Club Alpha 2, por lo que su Chip se encuentra desactivado definitivamente.</p>\r\n"
				+ "            <div class=\"opps-instructions\">\r\n"
				+ "                <small>\r\n"
				+ "                    Para más información acuda al Club o comuníquese al teléfono de atención a clientes 222 243 10 34 Ext. 127\r\n"
				+ "                    Atentamente:\r\n"
				+ "                    Club Alpha 2\r\n"
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
    
   
    
    
    /*String mensaje = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n"
    		+ "        <title>Rutina Entrenamiento</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- *//*\r\n"
    		+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- *//*\r\n"
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
    		+ "</html>";*/

	String mensaje = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
			"\n" +
			"<head>\n" +
			"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
			"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
			"    <title>Reporte de rutinas</title>\n" +
			"\n" +
			"    <style>\n" +
			"        *,\n" +
			"        *::after,\n" +
			"        *::before {\n" +
			"            box-sizing: border-box;\n" +
			"        }\n" +
			"\n" +
			"        body {\n" +
			"            margin: 0;\n" +
			"            font-family: sans-serif;\n" +
			"            /* background-color: #333; */\n" +
			"        }\n" +
			"\n" +
			"        img {\n" +
			"            /* max-width: 100%; */\n" +
			"            /* display: block; */\n" +
			"        }\n" +
			"\n" +
			"        .header {\n" +
			"            width: 768px;\n" +
			"            height: 185px;\n" +
			"            margin-right: auto;\n" +
			"            margin-left: auto;\n" +
			"            border-bottom: 35px solid rgb(52, 52, 52);\n" +
			"            margin-top: 50px;\n" +
			"            position: relative;\n" +
			"        }\n" +
			"\n" +
			"        .customer-image {\n" +
			"            display: inline-block;\n" +
			"            /* position: absolute; */\n" +
			"        }\n" +
			"\n" +
			"        .customer-info {\n" +
			"            /* width: 580px; */\n" +
			"            width: 618px;\n" +
			"            display: inline-block;\n" +
			"            height: 100%;\n" +
			"            /* position: absolute; */\n" +
			"            float: right;\n" +
			"            margin-top: 39px;\n" +
			"        }\n" +
			"\n" +
			"        .img-profile {\n" +
			"            width: 150px;\n" +
			"            height: 150px;\n" +
			"            box-shadow: 0px -3px 4px -2px rgb(0, 0, 0);\n" +
			"        }\n" +
			"\n" +
			"        .title {\n" +
			"            margin-bottom: 0;\n" +
			"            /* margin-top: 0; */\n" +
			"            font-size: 28px;\n" +
			"        }\n" +
			"\n" +
			"        .description {\n" +
			"            /* flex-grow: 1; */\n" +
			"            /* margin-top: 0; */\n" +
			"            /* margin-bottom: 27px; */\n" +
			"            font-size: 14px;\n" +
			"        }\n" +
			"\n" +
			"        .title,\n" +
			"        .description {\n" +
			"            padding-left: 16px;\n" +
			"        }\n" +
			"\n" +
			"        .customer-goal {\n" +
			"            background-color: rgb(224, 224, 224);\n" +
			"            box-shadow: 0px -3px 4px -2px rgb(0, 0, 0);\n" +
			"            font-size: 14px;\n" +
			"        }\n" +
			"\n" +
			"        .goal-item {\n" +
			"            padding-right: 1.5em;\n" +
			"            padding: 1em;\n" +
			"            display: inline-block;\n" +
			"        }\n" +
			"\n" +
			"        .goal-item:not(div:last-child) {\n" +
			"            box-shadow: 3px 0px 4px -2px rgb(0, 0, 0, 0);\n" +
			"        }\n" +
			"\n" +
			"        .goal-item p {\n" +
			"            margin: 0;\n" +
			"        }\n" +
			"\n" +
			"        .goal-item p:first-child {\n" +
			"            font-weight: 700;\n" +
			"        }\n" +
			"\n" +
			"        .goal-item p:last-child {\n" +
			"            margin-top: 0;\n" +
			"            color: rgb(104, 101, 101);\n" +
			"        }\n" +
			"\n" +
			"\n" +
			"        /* CONTENIDO PRINCIPAL */\n" +
			"\n" +
			"        .main {\n" +
			"            margin-left: auto;\n" +
			"            margin-right: auto;\n" +
			"            /*height: 300px;*/\n" +
			"        }\n" +
			"\n" +
			"        .rutina-title {\n" +
			"            font-size: 12px;\n" +
			"            color: rgb(104, 101, 101);\n" +
			"        }\n" +
			"\n" +
			"        .subtitle {\n" +
			"            color: rgb(239, 1, 11);\n" +
			"            font-size: 20px;\n" +
			"        }\n" +
			"\n" +
			"        .subtitle,\n" +
			"        .comments {\n" +
			"            text-align: center;\n" +
			"            font-size: 14px;\n" +
			"        }\n" +
			"\n" +
			"\n" +
			"        .workouts {\n" +
			"            margin-left: auto;\n" +
			"            margin-right: auto;\n" +
			"            /*background-color: red; */\n" +
			"        }\n" +
			"\n" +
			"        .workout {\n" +
			"            width: 50%;\n" +
			"            display: inline-block;\n" +
			"            margin: 0 -2px;\n" +
			"            /*height: 213px;*/\n" +
			"            position: relative;\n" +
			"        }\n" +
			"\n" +
			"        .machine,\n" +
			"        .series {\n" +
			"            width: 50%;\n" +
			"            display: inline-block;\n" +
			"            margin: 0 -1px;\n" +
			"        }\n" +
			"\n" +
			"        .machine-name {\n" +
			"            width: 100%;\n" +
			"        }\n" +
			"\n" +
			"        .machine span,\n" +
			"        .series span {\n" +
			"            font-weight: 700;\n" +
			"        }\n" +
			"\n" +
			"        .machine span:first-child {\n" +
			"            padding-left: 1em;\n" +
			"            padding-right: 1em;\n" +
			"            background-color: rgb(239, 1, 11);\n" +
			"            color: white;\n" +
			"            display: inline-block;\n" +
			"            width: 19%;\n" +
			"        }\n" +
			"\n" +
			"        .machine-name {\n" +
			"            display: flex;\n" +
			"            align-items: center;\n" +
			"        }\n" +
			"\n" +
			"        .machine-name span,\n" +
			"        .series span {\n" +
			"            padding: 0.3em 0;\n" +
			"        }\n" +
			"\n" +
			"        .machine-name span:nth-child(2) {\n" +
			"            background-color: rgb(146, 146, 146);\n" +
			"            text-align: center;\n" +
			"            width: 81%;\n" +
			"            color: white;\n" +
			"            display: inline-block;\n" +
			"        }\n" +
			"\n" +
			"        .machine-image {\n" +
			"            width: 180px;\n" +
			"            height: 180px;\n" +
			"        }\n" +
			"\n" +
			"        .series {\n" +
			"            text-align: center;\n" +
			"            /*height: 100%;*/\n" +
			"            float: right;\n" +
			"        }\n" +
			"\n" +
			"        .series>span {\n" +
			"            display: block;\n" +
			"            background-color: rgb(52, 52, 52);\n" +
			"            color: white;\n" +
			"        }\n" +
			"\n" +
			"        .series p {\n" +
			"            margin-top: 0;\n" +
			"            color: rgb(239, 1, 11);\n" +
			"            font-weight: 700;\n" +
			"            padding: 0 0.3em;\n" +
			"            text-align: left;\n" +
			"        }\n" +
			"\n" +
			"        .type {\n" +
			"            display: flex;\n" +
			"            padding: 0.5em;\n" +
			"            justify-content: space-between;\n" +
			"        }\n" +
			"\n" +
			"        .type span:last-child {\n" +
			"            color: rgb(144, 144, 144);\n" +
			"        }\n" +
			"\n" +
			"        .statistics-column img {\n" +
			"            display: inline-block;\n" +
			"            width: 18px;\n" +
			"            height: 18px;\n" +
			"        }\n" +
			"\n" +
			"        .fitness-statistics {\n" +
			"            display: grid;\n" +
			"            grid-template-columns: 50% 50%;\n" +
			"        }\n" +
			"\n" +
			"        .statistics-column {\n" +
			"            display: flex;\n" +
			"            flex-direction: column;\n" +
			"        }\n" +
			"\n" +
			"        .workout {\n" +
			"            font-size: 12px;\n" +
			"        }\n" +
			"\n" +
			"        .statistics-column .statistics-item:nth-child(odd) {\n" +
			"            background-color: rgb(228, 228, 228);\n" +
			"        }\n" +
			"\n" +
			"        .statistics-item {\n" +
			"            display: flex;\n" +
			"            align-items: center;\n" +
			"            padding: .2em .7em;\n" +
			"        }\n" +
			"\n" +
			"        .statistics-item span {\n" +
			"            padding-left: 0.3em;\n" +
			"        }\n" +
			"    </style>\n" +
			"</head>\n" +
			"\n" +
			"<body>\n" +
			"    <header class=\"header\">\n" +
			"        <div class=\"customer-image\">\n" +
			"            <img src=\"data:image/jpeg;base64,"+fotoCliente+"\"" +" alt=\"Profile image\" class=\"img-profile\"/>\n" +
			"        </div><div class=\"customer-info\">\n" +
			"            <h1 class=\"title\">HOLA, "+nombreCliente+" "+idCliente+"</h1>\n" +
			"            <p class=\"description\">"+club.toUpperCase()+" TE PRESENTA TU ENTRENAMIENTO PERSONALIZADO</p>\n" +
			"            <div class=\"customer-goal\">\n" +
			"                <div class=\"goal-item\">\n" +
			"                    <p>INICIO</p>\n" +
			"                    <p>"+inicio+"</p>\n" +
			"                </div>\n" +
			"                <div class=\"goal-item\">\n" +
			"                    <p>FIN</p>\n" +
			"                    <p>"+fin+"</p>\n" +
			"                </div>\n" +
			"                <div class=\"goal-item\">\n" +
			"                    <p>OBJETIVO</p>\n" +
			"                    <p>"+grupoMuscular+"</p>\n" +
			"                </div>\n" +
			"            </div>\n" +
			"        </div>\n" +
			"    </header>\n" +
			"\n" +
			"    <div class=\"main\">\n" +
			"        <p class=\"rutina-title\">RUTINA</p>\n" +
			"        <h2 class=\"subtitle\">"+segmento+"</h2>\n" +
			"        <p class=\"comments\">"+comentarios+"</p>\n" +
			"\n" +
			"        <div class=\"workouts\">"+listaEjercicios+"</div>\n" +
			"    </div>\n" +
			"</body>\n" +
			"\n" +
			"</html>";
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
		//Analiza el archivo
        Document document = Jsoup.parse(file, "UTF-8");
		//Establece la sintaxis de salida
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

		//Escribir el contenido de html en un archivo PDF
        try (OutputStream os = new FileOutputStream(ficheroPDF)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(ficheroPDF);
			//Un flujo de salida para generar el PDF resultante.
			builder.toStream(os);
			//Clase de ayuda para transformar Documenta a org.w3c.dom.Document
			//Convierta un documento jsoup en un documento W3C.
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
            //Ejecute la conversión de XHTML/XML a PDF y la salida a un flujo de salida establecido por toStream
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
		//Establecer el controlador de datos en el archivo adjunto y Obtener el archivo adjunto
    	contenido.setDataHandler(new DataHandler(new FileDataSource(ficheroPDF)));
    	// Establecer el nombre del archivo
		contenido.setFileName(ficheroPDF);
    	contenido2.setContent(texto, "text/html; charset=UTF-8");
	} catch (MessagingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//creamos un contenedor y le añadimos el cuerpo de texto
    MimeMultipart m =new MimeMultipart();
    try {
		m.addBodyPart(contenido2); //cuerpo del mensaje
		m.addBodyPart(contenido); //archivo adjunto
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
		//direccion "From"
        mail.setFrom((Address)new InternetAddress(this.correoEnvia));
		// Agregue las direcciones proporcionadas al tipo de destinatario especificado. //direccion "To"
        mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
        mail.setSubject(asunto);
		//Agregar el archivo adjunto y el contenido del mensaje m = contenido y contenido2
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
    
   
    
    
    /*String mensaje = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
    		+ "    <head>\r\n"
    		+ "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n"
    		+ "        <title>Rutina Entrenamiento</title>\r\n"
    		+ "        <style type=\"text/css\">\r\n"
    		+ "            /* Reset -------------------------------------------------------------------- *///\r\n"
    		/*+ "            * 	 { margin: 0;padding: 0; }\r\n"
    		+ "            body { font-size: 14px; }\r\n"
    		+ "\r\n"
    		+ "            /* OPPS --------------------------------------------------------------------- *///\r\n"
    		/*+ "\r\n"
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
    		+ "                margin: 20px auto;\r\n"
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
    		+ "                                    <img width=\"125\" height=\"125\" src=\"file:rutinas/pesaje/pesocorporal.png\"/>\r\n"
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
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"file:rutinas/pesaje/grasacorporal.png\"/>\r\n"
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
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"file:rutinas/pesaje/masamuscular.png\"/>\r\n"
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
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"file:rutinas/pesaje/adiposidadvisceral.png\"/>\r\n"
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
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"file:rutinas/pesaje/masaosea.png\"/>\r\n"
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
    		+ "                                    <img width=\"120 \" height=\"120\" src=\"file:rutinas/pesaje/liquidoscorporales.png\"/>\r\n"
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
    		+ "</html>";*/
	String mensaje = "\n" +
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
			"\n" +
			"<head>\n" +
			"\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
			"\t<title>Rutina Entrenamiento</title>\n" +
			"\t<style type=\"text/css\">\n" +
			"\t\t/* Reset -------------------------------------------------------------------- */\n" +
			"\t\t* {\n" +
			"\t\t\tmargin: 0;\n" +
			"\t\t\tpadding: 0;\n" +
			"\t\t}\n" +
			"\t\tbody {\n" +
			"\t\t\tfont-size: 14px;\n" +
			"\t\t}\n" +
			"\t\t/* OPPS --------------------------------------------------------------------- */\n" +
			"\t\th3 {\n" +
			"\t\t\tmargin-bottom: 5px;\n" +
			"\t\t\tfont-size: 15px;\n" +
			"\t\t\tfont-weight: 600;\n" +
			"\t\t\ttext-transform: uppercase;\n" +
			"\t\t\tcolor: #000000;\n" +
			"\t\t}\n" +
			"\t\t.opps {\n" +
			"\t\t\twidth: 700px;\n" +
			"\t\t\tborder-radius: 4px;\n" +
			"\t\t\tborder: 5px solid black;\n" +
			"\t\t\tbox-sizing: border-box;\n" +
			"\t\t\tpadding: 0 45px;\n" +
			"\t\t\tmargin: 20px auto;\n" +
			"\t\t\toverflow: hidden;\n" +
			"\t\t\tfont-family: 'Open Sans', sans-serif;\n" +
			"\t\t\tcolor: #4f5365;\n" +
			"\t\t}\n" +
			"\t\t.card {\n" +
			"\t\t\t/* width: auto;\n" +
			"\t\t\theight: 125px; */\n" +
			"\t\t\tborder-radius: 4px;\n" +
			"\t\t\tpadding: 2px 0px;\n" +
			"\t\t\toverflow: hidden;\n" +
			"\t\t\t/* border: 1px solid grey; */\n" +
			"\t\t\tfont-family: 'Open Sans', sans-serif;\n" +
			"\t\t\tcolor: #4f5365;\n" +
			"\t\t}\n" +
			"\t\t.card img {\n" +
			"\t\t\tfloat: left;\n" +
			"\t\t}\n" +
			"\t\t.card table {\n" +
			"\t\t\tfloat: right;\n" +
			"\t\t}\n" +
			"\t\t.opps-reminder {\n" +
			"\t\t\tposition: relative;\n" +
			"\t\t\ttop: -1px;\n" +
			"\t\t\tpadding: 9px 0 10px;\n" +
			"\t\t\tfont-size: 11px;\n" +
			"\t\t\ttext-transform: uppercase;\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tcolor: #ffffff;\n" +
			"\t\t\tbackground: #000000;\n" +
			"\t\t}\n" +
			"\t\t.opps-info {\n" +
			"\t\t\tmargin-top: 26px;\n" +
			"\t\t\tposition: relative;\n" +
			"\t\t}\n" +
			"\t\t.opps-info:after {\n" +
			"\t\t\tvisibility: hidden;\n" +
			"\t\t\tdisplay: block;\n" +
			"\t\t\tfont-size: 0;\n" +
			"\t\t\tcontent: \" \";\n" +
			"\t\t\tclear: both;\n" +
			"\t\t\theight: 0;\n" +
			"\t\t}\n" +
			"\t\t.opps-brand {\n" +
			"\t\t\twidth: 45%;\n" +
			"\t\t\tfloat: left;\n" +
			"\t\t}\n" +
			"\t\t.opps-brand img {\n" +
			"\t\t\tmax-width: 150px;\n" +
			"\t\t\tmargin-top: 2px;\n" +
			"\t\t}\n" +
			"\t\t.opps-ammount {\n" +
			"\t\t\twidth: 73%;\n" +
			"\t\t\theight: auto;\n" +
			"\t\t\tfloat: right;\n" +
			"\t\t}\n" +
			"\t\t.opps-ammount h2 {\n" +
			"\t\t\tfont-size: 15PX;\n" +
			"\t\t\tcolor: rgb(252, 0, 0);\n" +
			"\t\t\tline-height: 24px;\n" +
			"\t\t\tmargin-bottom: 72px;\n" +
			"\t\t}\n" +
			"\t\t.opps-ammount h2 sup {\n" +
			"\t\t\tfont-size: 16px;\n" +
			"\t\t\tposition: relative;\n" +
			"\t\t\ttop: -2px\n" +
			"\t\t}\n" +
			"\t\t.opps-ammount p {\n" +
			"\t\t\tfont-size: 10px;\n" +
			"\t\t\tline-height: 14px;\n" +
			"\t\t}\n" +
			"\t\t.opps-reference {\n" +
			"\t\t\tmargin-top: 2px;\n" +
			"\t\t\tmargin-bottom: 5px;\n" +
			"\t\t}\n" +
			"\t\th1 {\n" +
			"\t\t\tfont-size: 20px;\n" +
			"\t\t\tcolor: #000000;\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tmargin-top: -1px;\n" +
			"\t\t\tpadding: 6px 0 7px;\n" +
			"\t\t\tborder: 1px solid #b0afb5;\n" +
			"\t\t\tborder-radius: 4px;\n" +
			"\t\t\tbackground: #000000;\n" +
			"\t\t}\n" +
			"\t\t.opps-instructions {\n" +
			"\t\t\tmargin: 32px -45px 0;\n" +
			"\t\t\tpadding: 32px 45px 45px;\n" +
			"\t\t\tborder-top: 1px solid #b0afb5;\n" +
			"\t\t\tbackground: #f8f9fa;\n" +
			"\t\t}\n" +
			"\t\tol {\n" +
			"\t\t\tmargin: 17px 0 0 16px;\n" +
			"\t\t}\n" +
			"\t\tli+li {\n" +
			"\t\t\tmargin-top: 10px;\n" +
			"\t\t\tcolor: #000000;\n" +
			"\t\t}\n" +
			"\t\ta {\n" +
			"\t\t\tcolor: #1155cc;\n" +
			"\t\t}\n" +
			"\t\t.opps-footnote {\n" +
			"\t\t\tmargin-top: 22px;\n" +
			"\t\t\tpadding: 22px 20px 24px;\n" +
			"\t\t\tcolor: #108f30;\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tborder: 1px solid #108f30;\n" +
			"\t\t\tborder-radius: 4px;\n" +
			"\t\t\tbackground: #ffffff;\n" +
			"\t\t}\n" +
			"\t\timg{\n" +
			"\t\t\tborder-radius: 8px;\n" +
			"\t\t}\n" +
			"\t\ttable {\n" +
			"\t\t\twidth: 100%;\n" +
			"\t\t}\n" +
			"\t\ttd {\n" +
			"\t\t\tcolor: #636363;\n" +
			"\t\t\ttext-align: left;\n" +
			"\t\t\tfont-size: 11px;\n" +
			"\t\t}\n" +
			"\t\tth {\n" +
			"\t\t\theight: 40px;\n" +
			"\t\t\tfont-size: 11px;\n" +
			"\t\t}\n" +
			"\t\tth.info{\n" +
			"\t\t\theight: auto;\n" +
			"\t\t\twidth: 50%;\n" +
			"\t\t\tfont-size: 11px;\n" +
			"\t\t\talign-content: center;\n" +
			"\t\t}\n" +
			"\t\t.sub-title{\n" +
			"\t\t\tfont-size: 10px;\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tbackground: black;\n" +
			"\t\t\tcolor: white;\n" +
			"\t\t}\n" +
			"\t\th3 {\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t}\n" +
			"\t\th3.peso{\n" +
			"\t\t\tfont-size: 30px;\n" +
			"\t\t\talign-items: center;\n" +
			"\t\t\tmargin: 70px;\n" +
			"\t\t}\n" +
			"\t\timg.imagen-1{\n" +
			"\t\t\twidth: auto;\n" +
			"\t\t\theight: 170px;\n" +
			"\t\t\tfloat: left;\n" +
			"\t\t}\n" +
			"\t\tdiv.titulo-pesaje1{\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tbackground-color: orangered;\n" +
			"\t\t\tfont-size: 15px !important;\n" +
			"\t\t\tborder: solid orangered;\n" +
			"\t\t\tborder-radius: 15px 0 0 15px;\n" +
			"\t\t\tcolor: white;\n" +
			"\t\t}\n" +
			"\t\tdiv.titulo-pesaje2{\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tbackground-color: #FF9906;\n" +
			"\t\t\tfont-size: 15px !important;\n" +
			"\t\t\tborder: solid #FF9906;\n" +
			"\t\t\tborder-radius: 15px 0 0 15px;\n" +
			"\t\t\tcolor: white;\n" +
			"\t\t}\n" +
			"\t\tdiv.titulo-pesaje3{\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tbackground-color: #0AB881;\n" +
			"\t\t\tfont-size: 15px !important;\n" +
			"\t\t\tborder: solid #0AB881;\n" +
			"\t\t\tborder-radius: 15px 0 0 15px;\n" +
			"\t\t\tcolor: white;\n" +
			"\t\t}\n" +
			"\t\tdiv.titulo-pesaje4{\n" +
			"\t\t\ttext-align: center;\n" +
			"\t\t\tbackground-color: #068BEC;\n" +
			"\t\t\tfont-size: 14px !important;\n" +
			"\t\t\tborder: solid #068BEC;\n" +
			"\t\t\tborder-radius: 15px 0 0 15px;\n" +
			"\t\t\tcolor: white;\n" +
			"\t\t}\n" +
			"\t\tdiv.imagen-dato{\n" +
			"\t\t\twidth: auto;\n" +
			"\t\t\theight: auto;\n" +
			"\t\t}\n" +
			"\t\t.imagen-dato1{\n" +
			"\t\t\twidth: 110px;\n" +
			"\t\t\theight: 80px;\n" +
			"\t\t\talign-content: center;\n" +
			"\t\t}\n" +
			"\t\t.imagen-dato2{\n" +
			"\t\t\twidth: 110px;\n" +
			"\t\t\theight: 50px;\n" +
			"\t\t\tfloat: right;\n" +
			"\t\t}\n" +
			"\t\t.imagen-dato3{\n" +
			"\t\t\twidth: 120px;\n" +
			"\t\t\theight: 60px;\n" +
			"\t\t\tfloat: right;\n" +
			"\t\t}\n" +
			"\t\t.primero{\n" +
			"\t\t\twidth: 50%;\n" +
			"\t\t\tfloat: left;\n" +
			"\t\t}\n" +
			"\t\t.segundo{\n" +
			"\t\t\twidth: 50%;\n" +
			"\t\t\tfloat: right;\n" +
			"\t\t}\n" +
			"\t\t.tabla-datos{\n" +
			"\t\t\tfloat: right;\n" +
			"\t\t\twidth: 180px;\n" +
			"\t\t}\n" +
			"\t</style>\n" +
			"\t<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,600,700\" rel=\"stylesheet\" />\n" +
			"</head>\n" +
			"\n" +
			"<body>\n" +
			"\t<div class=\"opps\">\n" +
			"\t\t<div class=\"opps-header\">\n" +
			"\t\t\t<div class=\"opps-info\">\n" +
			"\t\t\t\t<img style=\"width: 150px; height: 204px;\" src=\"data:image/png;base64,"+fotoCliente+"\" />\n" +
			"\t\t\t\t<div class=\"opps-ammount\">\n" +
			"\t\t\t\t\t<h3>HOLA, "+nombre+" - "+idCliente+"</h3>\n" +
			"\t\t\t\t\t<h2>"+fecha+"</h2>\n" +
			"\t\t\t\t\t<table>\n" +
			"\t\t\t\t\t\t<thead>\n" +
			"\t\t\t\t\t\t\t<tr align=\"center\">\n" +
			"\t\t\t\t\t\t\t\t<th scope=\"col\">I.M.C.</th>\n" +
			"\t\t\t\t\t\t\t\t<th scope=\"col\">Edad Metabólica</th>\n" +
			"\t\t\t\t\t\t\t\t<th scope=\"col\">Metabolismo Basal</th>\n" +
			"\t\t\t\t\t\t\t\t<th scope=\"col\">Aporte cálorico diario</th>\n" +
			"\t\t\t\t\t\t\t</tr>\n" +
			"\t\t\t\t\t\t</thead>\n" +
			"\t\t\t\t\t\t<tbody>\n" +
			"\t\t\t\t\t\t\t<tr>\n" +
			"\t\t\t\t\t\t\t\t<td scope=\"row\">"+imc+"</td>\n" +
			"\t\t\t\t\t\t\t\t<td scope=\"row\">"+edadMetabolica+"</td>\n" +
			"\t\t\t\t\t\t\t\t<td scope=\"row\">"+metabolismoBasal+"</td>\n" +
			"\t\t\t\t\t\t\t\t<td scope=\"row\">"+calorias+"</td>\n" +
			"\t\t\t\t\t\t\t</tr>\n" +
			"\t\t\t\t\t\t</tbody>\n" +
			"\t\t\t\t\t</table>\n" +
			"\t\t\t\t</div>\n" +
			"\t\t\t</div>\n" +
			"\t\t</div>\n" +
			"\t\t<div class=\"sub-title\"><h2>Informe Avanzado en la Báscula</h2></div>\n" +
			"\t\t<div class='card-grid'>\n" +
			"\t\t\t<div class=\"card\">\n" +
			"\t\t\t\t<div class=\"primero\"><h3 class=\"peso\">"+pesoCorporal+" kg</h3></div>\n" +
			"\t\t\t\t<div class=\"segundo\">\n" +
			"\t\t\t\t\t<img class=\"imagen-1\" src=\"file:rutinas/pesaje/img/grasacorporal.png\">\n" +
			"\t\t\t\t\t<table class=\"tabla-datos\">\n" +
			"\t\t\t\t\t\t<tr><td><div class=\"titulo-pesaje1\">GRASA CORPORAL</div></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><h3>"+grasaCorporal+"%</h3></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><img  class=\"imagen-dato1\" src=\"file:rutinas/pesaje/img/grasacorporal-valores.png\"></td></tr>\n" +
			"\t\t\t\t\t</table>\n" +
			"\t\t\t\t</div>\n" +
			"\t\t\t</div>\n" +
			"\t\t\t<div class=\"card\">\n" +
			"\t\t\t\t<div class=\"primero\">\n" +
			"\t\t\t\t\t<table class=\"tabla-datos\">\n" +
			"\t\t\t\t\t\t<img class=\"imagen-1\" src=\"file:rutinas/pesaje/img/masamuscular.png\">\n" +
			"\t\t\t\t\t\t<tr><td><div class=\"titulo-pesaje2\">MASA MUSCULAR</div></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><h3>"+masaMuscular+" KG</h3></td></tr>\n" +
			"\t\t\t\t\t</table>\n" +
			"\t\t\t\t</div>\n" +
			"\t\t\t\t<div class=\"segundo\">\n" +
			"\t\t\t\t\t<table class=\"tabla-datos\">\n" +
			"\t\t\t\t\t\t<img class=\"imagen-1\" src=\"file:rutinas/pesaje/img/adiposidadvisceral.png\">\n" +
			"\t\t\t\t\t\t<tr><td><div class=\"titulo-pesaje3\">GRASA VISCERAL</div></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><h3>Nivel de Grasa: "+adiposidadVisceral+" KG</h3></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><img class=\"imagen-dato2\" src=\"file:rutinas/pesaje/img/adiposidadvisceral-valores.png\"></td></tr>\n" +
			"\t\t\t\t\t</table>\n" +
			"\t\t\t\t</div>\n" +
			"\t\t\t</div>\n" +
			"\t\t\t<div class=\"card\">\n" +
			"\t\t\t\t<div class=\"primero\">\n" +
			"\t\t\t\t\t<img class=\"imagen-1\" src=\"file:rutinas/pesaje/img/masaosea.png\">\n" +
			"\t\t\t\t\t<table class=\"tabla-datos\">\n" +
			"\t\t\t\t\t\t<tr><td><div class=\"titulo-pesaje3\">MASA ÓSEA</div></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><h3>"+masaOsea+" KG</h3></td></tr>\n" +
			"\t\t\t\t\t</table>\t\t\t\t\t\n" +
			"\t\t\t\t</div>\n" +
			"\t\t\t\t<div class=\"segundo\">\n" +
			"\t\t\t\t\t<img class=\"imagen-1\" src=\"file:rutinas/pesaje/img/liquidocorporal.png\">\n" +
			"\t\t\t\t\t<table class=\"tabla-datos\">\n" +
			"\t\t\t\t\t\t<tr><td><div class=\"titulo-pesaje4\">LÍQUIDOS CORPORALES</div></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><h3>"+liquidosCorporales+"%</h3></td></tr>\n" +
			"\t\t\t\t\t\t<tr><td><img class=\"imagen-dato3\" src=\"file:rutinas/pesaje/img/liquidocorporal-valores.png\"></td></tr>\n" +
			"\t\t\t\t\t</table>\n" +
			"\t\t\t\t\t\t\n" +
			"\t\t\t\t\t\n" +
			"\t\t\t\t\t\n" +
			"\t\t\t\t\t\n" +
			"\t\t\t\t\t\n" +
			"\t\t\t\t</div>\n" +
			"\t\t\t</div>\n" +
			"\t\t</div>\n" +
			"\t</div>\n" +
			"</body>\n" +
			"\n" +
			"</html>";
    
    String ruta = "prueba.html";
    String ficheroPDF = "plantilla.pdf"; 
    try {        
        File file = new File(ruta);
        // Si el archivo no existe es creado
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, Charset.forName("UTF-8"));
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

	public void sendBirthdayEmail(String subject, String club) {
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");
		propiedad.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(propiedad);

		BodyPart contenido = new MimeBodyPart();
		//BodyPart contenido2 = new MimeBodyPart();

		String bannerName = "";
		//String club = "Club Alpha 2";

		if(club.equals("Club Alpha 2") || club.equals("Club Alpha 3") || club.equals("Corporativo")) {
			bannerName = "alpha2.jpg";
		}else if(club.equals("CIMERA")) {
			bannerName = "cimera.jpg";
		}else {
			bannerName = "sports.jpg";
		}

		try {
			//Establecer el controlador de datos en el archivo adjunto y Obtener el archivo adjunto
			contenido.setDataHandler(new DataHandler(new FileDataSource(bannerName)));
			// Establecer el nombre del archivo
			contenido.setFileName(bannerName);
			//contenido2.setContent(texto, "text/html; charset=UTF-8");
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//creamos un contenedor y le añadimos el cuerpo de texto
		MimeMultipart m = new MimeMultipart();
		try {
			//m.addBodyPart(contenido2); //cuerpo del mensaje
			m.addBodyPart(contenido); //archivo adjunto
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MimeMessage mail = new MimeMessage(session);

		try {
			//direccion "From"
			mail.setFrom((Address)new InternetAddress(this.correoEnvia));
			// Agregue las direcciones proporcionadas al tipo de destinatario especificado. //direccion "To"

			//mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(to) });
			mail.addRecipients(Message.RecipientType.TO, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });

			mail.setSubject(subject);
			//Agregar el archivo adjunto y el contenido del mensaje m = contenido y contenido2
			mail.setContent(m, "text/html; charset=UTF-8");

			Transport transporte = session.getTransport("smtp");
			transporte.connect(correoEnvia,contrasena);

			//transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.BCC));
			transporte.sendMessage((Message)mail, mail.getRecipients(Message.RecipientType.TO));

			transporte.close();
			System.out.println("Correo enviado correctamente");
		} catch (AddressException ex) {
			Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void enviar_rutinanuevo(String asunto,String listaEjercicios) {

		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");
		propiedad.setProperty("mail.smtp.auth", "true");

		Session sesion = Session.getDefaultInstance(propiedad);

		String mensaje = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
				"\n" +
				"<head>\n" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
				"    <title>Reporte de rutinas</title>\n" +
				"\n" +
				"    <style>\n" +
				"        *,\n" +
				"        *::after,\n" +
				"        *::before {\n" +
				"            box-sizing: border-box;\n" +
				"        }\n" +
				"\n" +
				"@page {\n" +
				"  size: A4 portrait;\n" +
				"  margin: 0;\n" +
				"}" +
				"        body {\n" +
				"            margin: 0;\n" +
				"            font-family: sans-serif;\n" +
				"            /* background-color: #333; */\n" +
				"        }\n" +
				"\n" +
				"        img {\n" +
				"            max-width: 100%;\n" +
				"            /* display: block; */\n" +
				"        }\n" +
				"\n" +
				"        .header {\n" +
				"            width: 768px;\n" +
				"            height: 185px;\n" +
				"            margin-right: auto;\n" +
				"            margin-left: auto;\n" +
				"            border-bottom: 35px solid rgb(52, 52, 52);\n" +
				"            margin-top: 50px;\n" +
				"            position: relative;\n" +
				"        }\n" +
				"\n" +
				"        .customer-image {\n" +
				"            display: inline-block;\n" +
				"            /* position: absolute; */\n" +
				"        }\n" +
				"\n" +
				"        .customer-info {\n" +
				"            /* width: 580px; */\n" +
				"            width: 618px;\n" +
				"            display: inline-block;\n" +
				"            height: 100%;\n" +
				"            /* position: absolute; */\n" +
				"            float: right;\n" +
				"            margin-top: 39px;\n" +
				"        }\n" +
				"\n" +
				"        .img-profile {\n" +
				"            width: 150px;\n" +
				"            height: 150px;\n" +
				"            box-shadow: 0px -3px 4px -2px rgb(0, 0, 0);\n" +
				"        }\n" +
				"\n" +
				"        .title {\n" +
				"            margin-bottom: 0;\n" +
				"            /* margin-top: 0; */\n" +
				"            font-size: 28px;\n" +
				"        }\n" +
				"\n" +
				"        .description {\n" +
				"            /* flex-grow: 1; */\n" +
				"            /* margin-top: 0; */\n" +
				"            /* margin-bottom: 27px; */\n" +
				"            font-size: 14px;\n" +
				"        }\n" +
				"\n" +
				"        .title,\n" +
				"        .description {\n" +
				"            padding-left: 16px;\n" +
				"        }\n" +
				"\n" +
				"        .customer-goal {\n" +
				"            background-color: rgb(224, 224, 224);\n" +
				"            box-shadow: 0px -3px 4px -2px rgb(0, 0, 0);\n" +
				"            font-size: 14px;\n" +
				"        }\n" +
				"\n" +
				"        .goal-item {\n" +
				"            padding-right: 1.5em;\n" +
				"            padding: 1em;\n" +
				"            display: inline-block;\n" +
				"        }\n" +
				"\n" +
				"        .goal-item:not(div:last-child) {\n" +
				"            box-shadow: 3px 0px 4px -2px rgb(0, 0, 0, 0);\n" +
				"        }\n" +
				"\n" +
				"        .goal-item p {\n" +
				"            margin: 0;\n" +
				"        }\n" +
				"\n" +
				"        .goal-item p:first-child {\n" +
				"            font-weight: 700;\n" +
				"        }\n" +
				"\n" +
				"        .goal-item p:last-child {\n" +
				"            margin-top: 0;\n" +
				"            color: rgb(104, 101, 101);\n" +
				"        }\n" +
				"\n" +
				"\n" +
				"        /* CONTENIDO PRINCIPAL */\n" +
				"\n" +
				"        .main {\n" +
				"            /*margin-left: auto;*/\n" +
				"            /*margin-right: auto;*/\n" +
				"            /*height: 300px;*/\n" +
				"        }\n" +
				"\n" +
				"        .rutina-title {\n" +
				"            font-size: 12px;\n" +
				"            color: rgb(104, 101, 101);\n" +
				"        }\n" +
				"\n" +
				"        .subtitle {\n" +
				"            color: rgb(239, 1, 11);\n" +
				"            font-size: 20px;\n" +
				"        }\n" +
				"\n" +
				"        .subtitle,\n" +
				"        .comments {\n" +
				"            text-align: center;\n" +
				"            font-size: 14px;\n" +
				"        }\n" +
				"\n" +
				"\n" +
				"        .workouts {\n" +
				"            margin-left: auto;\n" +
				"            margin-right: auto;\n" +
				"            /*background-color: red; */\n" +
				"        }\n" +
				"\n" +
				"        .workout {\n" +
				"            width: 50%;\n" +
				"            display: inline-block;\n" +
				"            margin: 0 -2px;\n" +
				"            /*height: 213px;*/\n" +
				"            position: relative;\n" +
				"        }\n" +
				"\n" +
				"        .machine,\n" +
				"        .series {\n" +
				"            width: 50%;\n" +
				"            display: inline-block;\n" +
				"            margin: 0 -1px;\n" +
				"        }\n" +
				"\n" +
				"        .machine-name {\n" +
				"            width: 100%;\n" +
				"        }\n" +
				"\n" +
				"        .machine span,\n" +
				"        .series span {\n" +
				"            font-weight: 700;\n" +
				"        }\n" +
				"\n" +
				"        .machine span:first-child {\n" +
				"            padding-left: 1em;\n" +
				"            padding-right: 1em;\n" +
				"            background-color: rgb(239, 1, 11);\n" +
				"            color: white;\n" +
				"            display: inline-block;\n" +
				"            width: 19%;\n" +
				"        }\n" +
				"\n" +
				"        .machine-name {\n" +
				"            display: flex;\n" +
				"            align-items: center;\n" +
				"        }\n" +
				"\n" +
				"        .machine-name span,\n" +
				"        .series span {\n" +
				"            padding: 0.3em 0;\n" +
				"        }\n" +
				"\n" +
				"        .machine-name span:nth-child(2) {\n" +
				"            background-color: rgb(146, 146, 146);\n" +
				"            text-align: center;\n" +
				"            width: 81%;\n" +
				"            color: white;\n" +
				"            display: inline-block;\n" +
				"        }\n" +
				"\n" +
				"        .machine-image {\n" +
				"            width: 180px;\n" +
				"            height: 180px;\n" +
				"        }\n" +
				"\n" +
				"        .series {\n" +
				"            text-align: center;\n" +
				"            /*height: 100%;*/\n" +
				"            float: right;\n" +
				"        }\n" +
				"\n" +
				"        .series>span {\n" +
				"            display: block;\n" +
				"            background-color: rgb(52, 52, 52);\n" +
				"            color: white;\n" +
				"        }\n" +
				"\n" +
				"        .series p {\n" +
				"            margin-top: 0;\n" +
				"            color: rgb(239, 1, 11);\n" +
				"            font-weight: 700;\n" +
				"            padding: 0 0.3em;\n" +
				"            text-align: left;\n" +
				"        }\n" +
				"\n" +
				"        .type {\n" +
				"            display: flex;\n" +
				"            padding: 0.5em;\n" +
				"            justify-content: space-between;\n" +
				"        }\n" +
				"\n" +
				"        .type span:last-child {\n" +
				"            color: rgb(144, 144, 144);\n" +
				"        }\n" +
				"\n" +
				"        .statistics-column img {\n" +
				"            display: inline-block;\n" +
				"            width: 18px;\n" +
				"            height: 18px;\n" +
				"        }\n" +
				"\n" +
				"        .fitness-statistics {\n" +
				"            display: grid;\n" +
				"            grid-template-columns: 50% 50%;\n" +
				"        }\n" +
				"\n" +
				"        .statistics-column {\n" +
				"            display: flex;\n" +
				"            flex-direction: column;\n" +
				"        }\n" +
				"\n" +
				"        .workout {\n" +
				"            font-size: 12px;\n" +
				"        }\n" +
				"\n" +
				"        .statistics-column .statistics-item:nth-child(odd) {\n" +
				"            background-color: rgb(228, 228, 228);\n" +
				"        }\n" +
				"\n" +
				"        .statistics-item {\n" +
				"            display: flex;\n" +
				"            align-items: center;\n" +
				"            padding: .2em .7em;\n" +
				"        }\n" +
				"\n" +
				"        .statistics-item span {\n" +
				"            padding-left: 0.3em;\n" +
				"        }\n" +
				"    </style>\n" +
				"</head>\n" +
				"\n" +
				"<body>\n" +
				"    <div class=\"main\">\n" +
				"        <div class=\"\">"+listaEjercicios+"</div>\n" +
				"    </div>\n" +
				"</body>\n" +
				"\n" +
				"</html>";

		String ruta = "rutina_nueva.html";
		String ficheroPDF = "plantilla_rutina.pdf";
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

			//Analiza el archivo
			Document document = Jsoup.parse(file, "UTF-8");
			//Establece la sintaxis de salida
			document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

			//Escribir el contenido de html en un archivo PDF
			try (OutputStream os = new FileOutputStream(ficheroPDF)) {
				PdfRendererBuilder builder = new PdfRendererBuilder();
				builder.withUri(ficheroPDF);
				//Un flujo de salida para generar el PDF resultante.
				builder.toStream(os);
				//Clase de ayuda para transformar Documenta a org.w3c.dom.Document
				//Convierta un documento jsoup en un documento W3C.
				builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
				//Ejecute la conversión de XHTML/XML a PDF y la salida a un flujo de salida establecido por toStream
				builder.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		BodyPart contenido=new MimeBodyPart();

		try {
			//Establecer el controlador de datos en el archivo adjunto y Obtener el archivo adjunto
			contenido.setDataHandler(new DataHandler(new FileDataSource(ficheroPDF)));
			// Establecer el nombre del archivo
			contenido.setFileName(ficheroPDF);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//creamos un contenedor y le añadimos el cuerpo de texto
		MimeMultipart m =new MimeMultipart();
		try {
			//m.addBodyPart(contenido2); //cuerpo del mensaje
			m.addBodyPart(contenido); //archivo adjunto
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MimeMessage mail = new MimeMessage(sesion);

		try {
			//direccion "From"
			mail.setFrom((Address)new InternetAddress(this.correoEnvia));
			// Agregue las direcciones proporcionadas al tipo de destinatario especificado. //direccion "To"
			mail.addRecipients(Message.RecipientType.BCC, (Address[])new InternetAddress[] { new InternetAddress(this.destinatario) });
			mail.setSubject(asunto);
			//Agregar el archivo adjunto y el contenido del mensaje m = contenido y contenido2
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



