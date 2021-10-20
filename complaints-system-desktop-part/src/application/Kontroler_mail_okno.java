package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Kontroler_mail_okno implements Initializable {

	@FXML
	TextField nadawca_mail, odbiorca_mail, status_mail;
	@FXML
	TextArea tresc_mail;
	@FXML
	Separator linia_mail;
	@FXML
	Button button_mail;

	@FXML
	private void wyslij_maila(ActionEvent event) throws AddressException, MessagingException {
		Wysylanie_maila.wyslij_maila("szymon87.p@gmail.com", odbiorca_mail.getText(), tresc_mail.getText(), event);
	}

	public void ustaw_dane_poczatkowe(Obsluga_tableview zaznaczony_wiersz) {
		nadawca_mail.setText("powiadomienia.reklamacje@firma.pl");
		odbiorca_mail.setText(zaznaczony_wiersz.getEmail());
		status_mail.setText(zaznaczony_wiersz.getStatus());
		tresc_mail.setText("Informujemy, że Państwa reklamacja została rozpatrzona. Aktualny status reklamacji, to "
				+ zaznaczony_wiersz.getStatus() + ".");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}
