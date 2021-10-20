package application;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Kontroler_logowanie implements Initializable {

	@FXML
	private PasswordField password;
	@FXML
	private Label info, zalog_uzytkownik;
	@FXML
	private Button logowanie;
	@FXML
	private TextField uzytkownik;
	public static String value_user;

	@FXML
	private void logowanie_action(ActionEvent event)
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		value_user = uzytkownik.getText();
		String value_password = password.getText();
		String tresc_zapytania = "SELECT haslo, login FROM logowanie WHERE login = '" + value_user + "'";
		String nieprawidlowe_swiadczenia = "Nieprawidłowa nazwa użytkownika lub hasło";
		Polaczenie_z_baza przeslij_zapytanie = new Polaczenie_z_baza();
		Weryfikacja_hasla weryfikuj_haslo = new Weryfikacja_hasla();

		// Hashowanie_hasla hashuj_haslo = new Hashowanie_hasla();
		// String zahashowane_haslo =
		// hashuj_haslo.generateStorngPasswordHash(value_password);
		// System.out.println(zahashowane_haslo);

		ResultSet wynik_zapytania = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

		while (wynik_zapytania.next()) {
			String zahashowane_haslo = wynik_zapytania.getString("haslo");
			boolean zweryfikowane_haslo = weryfikuj_haslo.validatePassword(value_password, zahashowane_haslo);
			if (!zweryfikowane_haslo) {
				info.setText(nieprawidlowe_swiadczenia);
			} else {
				Nowe_okno stworz_glowne_okno = new Nowe_okno();
				(((Node) event.getSource())).getScene().getWindow().hide();
				stworz_glowne_okno.pokaz_okno(0, 1430, 900, "Glowne_okno.fxml", "System Obiegu Reklamacji");
			}
		}

		if (!wynik_zapytania.isAfterLast())
			info.setText(nieprawidlowe_swiadczenia);
	}

	public final static String zalogowany_uzytkownik() {
		return value_user;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}
