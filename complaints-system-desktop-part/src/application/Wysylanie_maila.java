package application;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;

@SuppressWarnings("unused")
public class Wysylanie_maila {

	public static void wyslij_maila(String nadawca, String odbiorca, String tresc, ActionEvent event) {
		final String username = nadawca;
		final String password = "DR93Hjsd34";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(nadawca));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(odbiorca));
			message.setSubject("Twoja reklamacja została rozpatrzona");
			message.setText(tresc);

			Transport.send(message);
			(((Node) event.getSource())).getScene().getWindow().hide();

		} catch (MessagingException e) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Uwaga");
			alert.setHeaderText("Nie udało się wysłać wiadomości.");
			alert.setContentText("Sprawdź połączenie z Internetem");
			alert.showAndWait();
		}
	}
}