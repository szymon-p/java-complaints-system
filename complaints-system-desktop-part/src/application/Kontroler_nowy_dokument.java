package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Kontroler_nowy_dokument implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Separator linia1, linia2;
	@FXML
	private TextArea tresc_dokumentu_textArea;
	@FXML
	private TextField nazwisko_kontr_TextField, imie_kontr_TextField, ulica_TextField, kod_pocztowy_TextField,
			miasto_TextField, nr_zam_TextField, email_TextField;
	@FXML
	private ComboBox<String> status_ComboBox, przypisana_osoba_ComboBox;

	private Wyswietlenie_danych_ComboBox wyswietlenie_danych_zakladka_dokument = new Wyswietlenie_danych_ComboBox();
	private String tresc_zapytania;
	private Polaczenie_z_baza przeslij_zapytanie = new Polaczenie_z_baza();

	public void zapisz_zmiana_nowy_dokument(Event event) throws SQLException {

		if (sprawdz_formularz()) {

			int id_stat = wyswietlenie_danych_zakladka_dokument.daj_id_stat_statusu(status_ComboBox.getValue());
			int id_uzytk = wyswietlenie_danych_zakladka_dokument
					.daj_id_uzytk_uzytkownika(przypisana_osoba_ComboBox.getValue());

			int count_kontr = sprawdz_lp_kontr();

			tresc_zapytania = "INSERT INTO kontrahent (IMIE_KONTR, NAZWISKO_KONTR, ULICA, KOD_POCZTOWY, MIASTO, MAIL, NR_ZAM, ID_KONTR) VALUES"
					+ "('" + imie_kontr_TextField.getText() + "','" + nazwisko_kontr_TextField.getText() + "','"
					+ ulica_TextField.getText() + "','" + kod_pocztowy_TextField.getText() + "','"
					+ miasto_TextField.getText() + "','" + email_TextField.getText() + "','"
					+ nr_zam_TextField.getText() + "','" + count_kontr + "');";

			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

			int count_rek = sprawdz_lp_rek();

			tresc_zapytania = "INSERT INTO dokument (ID_REK, ID_STAT, ID_UZYTK, ID_KONTR, DATA_DODANIA, DATA_ZAMKNIECIA, TRESC_DOKUMENTU) VALUES"
					+ "('" + count_rek + "','" + id_stat + "','" + id_uzytk + "','" + count_kontr + "','"
					+ LocalDate.now() + "',null,'" + tresc_dokumentu_textArea.getText() + "');";

			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

			tresc_zapytania = "INSERT INTO historia (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI) VALUES"
					+ "('Dodanie dokumentu','brak dokumentu','nowy dokument','" + count_rek + "','"
					+ Kontroler_logowanie.zalogowany_uzytkownik() + "','" + pokaz_aktualna_date() + "');";

			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		} else
			event.consume();
	}

	private Boolean sprawdz_formularz() {

		if (imie_kontr_TextField.getText().trim().equals("") || nazwisko_kontr_TextField.getText().trim().equals("")
				|| ulica_TextField.getText().trim().equals("") || kod_pocztowy_TextField.getText().trim().equals("")
				|| miasto_TextField.getText().trim().equals("") || email_TextField.getText().trim().equals("")
				|| nr_zam_TextField.getText().trim().equals("")
				|| tresc_dokumentu_textArea.getText().trim().equals("") || status_ComboBox.getValue().equals("")
				|| przypisana_osoba_ComboBox.getValue().equals("")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Uwaga");
			alert.setHeaderText("Wymagane pola formularza");
			alert.setContentText("Wszystkie pola formularza są wymagane.");
			alert.showAndWait();
			return false;
		} else
			return true;
	}

	private String pokaz_aktualna_date() {
		Date aktualna_data = new Date();
		DateFormat formatowanie_daty = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		return formatowanie_daty.format(aktualna_data);
	}

	private int sprawdz_lp_kontr() throws SQLException {
		int count = 0;
		String zapytanie_id_kontr = "SELECT COUNT(*) liczba_wystapien FROM kontrahent;";
		ResultSet wynik_zapytania_count = null;
		wynik_zapytania_count = przeslij_zapytanie.zapytanie_do_bazy(zapytanie_id_kontr);

		try {
			while (wynik_zapytania_count.next()) {
				count = wynik_zapytania_count.getInt("liczba_wystapien") + 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return count;
	}

	private int sprawdz_lp_rek() throws SQLException {
		int count = 0;
		String zapytanie_id_rek = "SELECT COUNT(*) liczba_wystapien FROM dokument;";
		ResultSet wynik_zapytania_count = null;
		wynik_zapytania_count = przeslij_zapytanie.zapytanie_do_bazy(zapytanie_id_rek);

		try {
			while (wynik_zapytania_count.next()) {
				count = wynik_zapytania_count.getInt("liczba_wystapien") + 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return count;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AnchorPane.setTopAnchor(tresc_dokumentu_textArea, 349.0);
		AnchorPane.setLeftAnchor(tresc_dokumentu_textArea, 25.0);
		AnchorPane.setRightAnchor(tresc_dokumentu_textArea, 25.0);
		AnchorPane.setTopAnchor(linia1, 22.0);
		AnchorPane.setLeftAnchor(linia1, 151.0);
		AnchorPane.setRightAnchor(linia1, 10.0);
		AnchorPane.setTopAnchor(linia2, 239.0);
		AnchorPane.setLeftAnchor(linia2, 107.0);
		AnchorPane.setRightAnchor(linia2, 10.0);

		try {
			przypisana_osoba_ComboBox.setItems(wyswietlenie_danych_zakladka_dokument.daj_liste_uzytkownikow(0));
			status_ComboBox.setItems(wyswietlenie_danych_zakladka_dokument.daj_liste_statusow(-1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
