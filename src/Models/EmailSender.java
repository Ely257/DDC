package Models;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class EmailSender {

    private static final String emailFrom = "corzojosedavid15@gmail.com";
    private static final String passwordFrom = "pcql fhox gfpe wlbg";   

    public static void enviarCorreoConAdjunto(String emailTo, String subject, String contentHtml, File archivoPdf) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", emailFrom);
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage mensaje = new MimeMessage(session);

        try {
            mensaje.setFrom(new InternetAddress(emailFrom));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mensaje.setSubject(subject);

            MimeBodyPart cuerpoMensaje = new MimeBodyPart();
            cuerpoMensaje.setContent(contentHtml, "text/html; charset=ISO-8859-1");

            MimeBodyPart adjunto = new MimeBodyPart();
            DataSource fuente = new FileDataSource(archivoPdf);
            adjunto.setDataHandler(new DataHandler(fuente));
            adjunto.setFileName(archivoPdf.getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoMensaje);
            multipart.addBodyPart(adjunto);

            mensaje.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();

            JOptionPane.showMessageDialog(null, "Correo enviado con PDF adjunto");

        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al enviar el correo: " + ex.getMessage());
        }
    }
}
