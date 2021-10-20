package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Kontroler_zakladka_dokument implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Separator linia1, linia2, linia3;
	@FXML
	private TableView<Obsluga_tableview> tab_hist_dok;
	@FXML
	private TableColumn<Obsluga_tableview, String> tab_hist_data_oper, tab_hist_opis_oper, tab_hist_dane_przed,
			tab_hist_dane_po, tab_hist_uzytk;
	@FXML
	private TextArea tresc_dokumentu_textArea;
	@FXML
	private TextField nazwisko_kontr_TextField, imie_kontr_TextField, ulica_TextField, kod_pocztowy_TextField,
			miasto_TextField, nr_zam_TextField, email_TextField;
	@FXML
	private ComboBox<String> status_ComboBox, przypisana_osoba_ComboBox;
	@FXML
	private DatePicker data_dodania_DatePicker, data_zamkniecia_DatePicker;
	private Wyswietlenie_danych_ComboBox wyswietlenie_danych_zakladka_dokument = new Wyswietlenie_danych_ComboBox();
	private String tresc_zapytania;
	private Object pierwotna_data_dodania, pierwotna_data_zamkniecia;
	private String pierwotne_imie_kontr, pierwotne_nazwisko_kontr, pierwotna_ulica, pierwotny_nr_zam, pierwotne_miasto,
			pierwotny_kod_pocztowy, pierwotny_email;
	private Polaczenie_z_baza przeslij_zapytanie = new Polaczenie_z_baza();
	private ObservableList<Obsluga_tableview> kolekcja_danych_tab_hist = FXCollections.observableArrayList();

	public boolean pokazywanie_zapisywania(Obsluga_tableview zaznaczony_wiersz) {

		Boolean wartosc_przypisany_uzytkownik, wartosc_status, wartosc_data_dodania;

		if (zaznaczony_wiersz.getPrzypisany_uzytkownik().equals(przypisana_osoba_ComboBox.getValue())
				|| przypisana_osoba_ComboBox.getValue() == null)
			wartosc_przypisany_uzytkownik = true;
		else
			wartosc_przypisany_uzytkownik = false;

		if (zaznaczony_wiersz.getStatus().equals(status_ComboBox.getValue())
				|| status_ComboBox.getValue() == null)
			wartosc_status = true;
		else
			wartosc_status = false;

		if (zaznaczony_wiersz.getData_dodania().equals(data_dodania_DatePicker.getValue())
				|| data_dodania_DatePicker.getValue() == null)
			wartosc_data_dodania = true;
		else
			wartosc_data_dodania = false;

		if (zaznaczony_wiersz.getNazwisko_kontrahenta().equals(nazwisko_kontr_TextField.getText())
				&& zaznaczony_wiersz.getImie_kontrahenta().equals(imie_kontr_TextField.getText())
				&& zaznaczony_wiersz.getUlica().equals(ulica_TextField.getText())
				&& zaznaczony_wiersz.getNr_zam().equals(nr_zam_TextField.getText())
				&& zaznaczony_wiersz.getMiasto().equals(miasto_TextField.getText())
				&& zaznaczony_wiersz.getKod_pocztowy().equals(kod_pocztowy_TextField.getText())
				&& zaznaczony_wiersz.getEmail().equals(email_TextField.getText()) && wartosc_status
				&& wartosc_przypisany_uzytkownik && wartosc_data_dodania)
			return false;
		else
			return true;
	}

	public void zapisz_zmiana_zakladka_dokument(Obsluga_tableview zaznaczony_wiersz) throws SQLException, IOException {

		pierwotna_data_dodania = zaznaczony_wiersz.getData_dodania();
		pierwotna_data_zamkniecia = zaznaczony_wiersz.getData_zamkniecia();
		pierwotne_imie_kontr = zaznaczony_wiersz.getImie_kontrahenta();
		pierwotne_nazwisko_kontr = zaznaczony_wiersz.getNazwisko_kontrahenta();
		pierwotna_ulica = zaznaczony_wiersz.getUlica();
		pierwotny_nr_zam = zaznaczony_wiersz.getNr_zam();
		pierwotne_miasto = zaznaczony_wiersz.getMiasto();
		pierwotny_kod_pocztowy = zaznaczony_wiersz.getKod_pocztowy();
		pierwotny_email = zaznaczony_wiersz.getEmail();

		zaznaczony_wiersz.setNazwisko_kontrahenta(nazwisko_kontr_TextField.getText());
		zaznaczony_wiersz.setImie_kontrahenta(imie_kontr_TextField.getText());
		zaznaczony_wiersz.setUlica(ulica_TextField.getText());
		zaznaczony_wiersz.setNr_zam(nr_zam_TextField.getText());
		zaznaczony_wiersz.setMiasto(miasto_TextField.getText());
		zaznaczony_wiersz.setKod_pocztowy(kod_pocztowy_TextField.getText());
		zaznaczony_wiersz.setEmail(email_TextField.getText());

		if (!(przypisana_osoba_ComboBox.getValue() == null))
			zaznaczony_wiersz.setPrzypisany_uzytkownik(przypisana_osoba_ComboBox.getValue());
		if (!(data_dodania_DatePicker.getValue() == null))
			zaznaczony_wiersz.setData_dodania(data_dodania_DatePicker.getValue());

		if (!(status_ComboBox.getValue() == null)) {

			zaznaczony_wiersz.setStatus(status_ComboBox.getValue());

			if (status_ComboBox.getValue().substring(0, status_ComboBox.getValue().indexOf(" ")).equals("zakończony")) {
				LocalDate aktualna_data = LocalDate.now();
				data_zamkniecia_DatePicker.setValue(aktualna_data);
				zaznaczony_wiersz.setData_zamkniecia(data_zamkniecia_DatePicker.getValue());

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("Mail_okno.fxml"));
				AnchorPane anchorPane = loader.load();
				Kontroler_mail_okno kontroler_mail_okno = loader.getController();

				Scene scene = new Scene(anchorPane);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("System Obiegu Reklamacji - Skrzynka Nadawcza");
				stage.getIcons().add(new Image(Main.class.getResourceAsStream("ikona.png")));
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);
				stage.show();

				kontroler_mail_okno.ustaw_dane_poczatkowe(zaznaczony_wiersz);

				stage.setOnCloseRequest(event -> {

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Wysyłanie maila");
					alert.setHeaderText(
							"Opuszczenie okna spowoduje przerwanie procesu wysyłki. Klient nie otrzyma wiadomości e-mail.");
					alert.setContentText("Czy na pewno chcesz kontynuować?");

					ButtonType takButton = new ButtonType("Tak");
					ButtonType nieButton = new ButtonType("Nie");

					alert.getButtonTypes().setAll(takButton, nieButton);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == takButton)
						;

					else if (result.get() == nieButton)
						event.consume();
				});
			}
		}

		zapisz_zmiany_do_bazy(zaznaczony_wiersz);
	}

	public String pokaz_aktualna_date() {
		Date aktualna_data = new Date();
		DateFormat formatowanie_daty = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		return formatowanie_daty.format(aktualna_data);
	}

	private void wyswietlenie_tabeli_historia(Obsluga_tableview zaznaczony_wiersz) throws SQLException {
		tab_hist_dok.getItems().clear();
		tresc_zapytania = "SELECT * FROM historia WHERE id_rek = " + zaznaczony_wiersz.getId_rek()
				+ " ORDER BY data_operacji DESC;";
		przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		ResultSet wynik_zapytania = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		while (wynik_zapytania.next()) {
			Obsluga_tableview tview = new Obsluga_tableview();
			tview.hist_data_operacji.set(wynik_zapytania.getString("data_operacji"));
			tview.hist_opis_operacji.set(wynik_zapytania.getString("opis_operacji"));
			tview.hist_dane_przed_zmiana.set(wynik_zapytania.getString("dane_przed_zmiana"));
			tview.hist_dane_po_zmianie.set(wynik_zapytania.getString("dane_po_zmianie"));
			tview.hist_uzytk.set(wynik_zapytania.getString("uzytkownik"));
			kolekcja_danych_tab_hist.add(tview);
		}
		tab_hist_dok.setItems(kolekcja_danych_tab_hist);
	}

	private void zapisz_historie_dokumentu(Obsluga_tableview zaznaczony_wiersz) throws SQLException {

		if (!(przypisana_osoba_ComboBox.getValue() == null)) {
			if (!przypisana_osoba_ComboBox.getPromptText().equals(przypisana_osoba_ComboBox.getValue())) {
				tresc_zapytania = "INSERT INTO historia"
						+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
						+ " VALUES ( 'Zmiana przypisanej osoby', '" + przypisana_osoba_ComboBox.getPromptText() + "', '"
						+ przypisana_osoba_ComboBox.getValue() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
						+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
				przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
			}
		}

		if (!(data_dodania_DatePicker.getValue() == null)) {
			if (!data_dodania_DatePicker.getValue().equals(pierwotna_data_dodania)) {
				tresc_zapytania = "INSERT INTO historia"
						+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
						+ " VALUES ( 'Zmiana daty dodania dokumentu', '" + pierwotna_data_dodania + "', '"
						+ data_dodania_DatePicker.getValue() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
						+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
				przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
			}
		}

		if (!pierwotne_imie_kontr.equals(imie_kontr_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana imienia kontrahenta', '" + pierwotne_imie_kontr + "', '"
					+ imie_kontr_TextField.getText() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
					+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!pierwotne_nazwisko_kontr.equals(nazwisko_kontr_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana nazwiska kontrahenta', '" + pierwotne_nazwisko_kontr + "', '"
					+ nazwisko_kontr_TextField.getText() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
					+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!pierwotna_ulica.equals(ulica_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana ulicy kontrahenta', '" + pierwotna_ulica + "', '" + ulica_TextField.getText()
					+ "', '" + zaznaczony_wiersz.getId_rek() + "','" + Kontroler_logowanie.zalogowany_uzytkownik()
					+ "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!pierwotny_nr_zam.equals(nr_zam_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana numeru zamieszkania kontrahenta', '" + pierwotny_nr_zam + "', '"
					+ nr_zam_TextField.getText() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
					+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!pierwotne_miasto.equals(miasto_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana miasta kontrahenta', '" + pierwotne_miasto + "', '"
					+ miasto_TextField.getText() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
					+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!pierwotny_kod_pocztowy.equals(kod_pocztowy_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana kodu pocztowego kontrahenta', '" + pierwotny_kod_pocztowy + "', '"
					+ kod_pocztowy_TextField.getText() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
					+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!pierwotny_email.equals(email_TextField.getText())) {
			tresc_zapytania = "INSERT INTO historia"
					+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
					+ " VALUES ( 'Zmiana e-maila kontrahenta', '" + pierwotny_email + "', '" + email_TextField.getText()
					+ "', '" + zaznaczony_wiersz.getId_rek() + "','" + Kontroler_logowanie.zalogowany_uzytkownik()
					+ "', '" + pokaz_aktualna_date() + "');";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

		}

		if (!(data_zamkniecia_DatePicker.getValue() == null)) {
			if (!data_zamkniecia_DatePicker.getValue().equals(pierwotna_data_zamkniecia)) {
				tresc_zapytania = "INSERT INTO historia"
						+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
						+ " VALUES ( 'Zmiana daty zamknięcia dokumentu', '" + pierwotna_data_zamkniecia + "', '"
						+ data_zamkniecia_DatePicker.getValue() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
						+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
				przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
			}
		}

		if (!(status_ComboBox.getValue() == null)) {
			if (!status_ComboBox.getPromptText().equals(status_ComboBox.getValue())) {
				tresc_zapytania = "INSERT INTO historia"
						+ " (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI)"
						+ " VALUES ( 'Zmiana statusu dokumentu', '" + status_ComboBox.getPromptText() + "', '"
						+ status_ComboBox.getValue() + "', '" + zaznaczony_wiersz.getId_rek() + "','"
						+ Kontroler_logowanie.zalogowany_uzytkownik() + "', '" + pokaz_aktualna_date() + "');";
				przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
			}
		}
	}

	private void zapisz_zmiany_do_bazy(Obsluga_tableview zaznaczony_wiersz) throws SQLException {

		zapisz_historie_dokumentu(zaznaczony_wiersz);

		if (!(status_ComboBox.getValue() == null)) {
			tresc_zapytania = "UPDATE dokument SET id_stat = "
					+ wyswietlenie_danych_zakladka_dokument.daj_id_stat_statusu(status_ComboBox.getValue())
					+ " WHERE id_rek=" + zaznaczony_wiersz.getId_rek() + ";";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!(przypisana_osoba_ComboBox.getValue() == null)) {
			tresc_zapytania = "UPDATE dokument SET id_uzytk = "
					+ wyswietlenie_danych_zakladka_dokument
							.daj_id_uzytk_uzytkownika(przypisana_osoba_ComboBox.getValue())
					+ " WHERE id_rek=" + zaznaczony_wiersz.getId_rek() + ";";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!(data_dodania_DatePicker.getValue() == null)) {
			tresc_zapytania = "UPDATE dokument SET data_dodania = '" + data_dodania_DatePicker.getValue() + "'"
					+ " WHERE id_rek=" + zaznaczony_wiersz.getId_rek() + ";";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		if (!(data_zamkniecia_DatePicker.getValue() == null)) {
			tresc_zapytania = "UPDATE dokument SET data_zamkniecia = '" + data_zamkniecia_DatePicker.getValue() + "'"
					+ " WHERE id_rek=" + zaznaczony_wiersz.getId_rek() + ";";
			przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		}

		tresc_zapytania = "UPDATE kontrahent SET imie_kontr='" + imie_kontr_TextField.getText() + "', nazwisko_kontr='"
				+ nazwisko_kontr_TextField.getText() + "', ulica='" + ulica_TextField.getText() + "', nr_zam='"
				+ nr_zam_TextField.getText() + "', miasto='" + miasto_TextField.getText() + "', kod_pocztowy='"
				+ kod_pocztowy_TextField.getText() + "', mail='" + email_TextField.getText() + "'" + " WHERE id_kontr="
				+ zaznaczony_wiersz.getId_kontr() + ";";
		przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
	}

	public void setZaznaczony_wiersz(Obsluga_tableview zaznaczony_wiersz) throws SQLException {

		wyswietlenie_tabeli_historia(zaznaczony_wiersz);

		nazwisko_kontr_TextField.setText(zaznaczony_wiersz.getNazwisko_kontrahenta());
		imie_kontr_TextField.setText(zaznaczony_wiersz.getImie_kontrahenta());
		ulica_TextField.setText(zaznaczony_wiersz.getUlica());
		nr_zam_TextField.setText(zaznaczony_wiersz.getNr_zam());
		miasto_TextField.setText(zaznaczony_wiersz.getMiasto());
		kod_pocztowy_TextField.setText(zaznaczony_wiersz.getKod_pocztowy());
		email_TextField.setText(zaznaczony_wiersz.getEmail());
		status_ComboBox.setPromptText(zaznaczony_wiersz.getStatus());
		przypisana_osoba_ComboBox.setPromptText(zaznaczony_wiersz.getPrzypisany_uzytkownik());
		data_dodania_DatePicker.setValue(zaznaczony_wiersz.getData_dodania());
		data_zamkniecia_DatePicker.setValue(zaznaczony_wiersz.getData_zamkniecia());
		tresc_dokumentu_textArea.setText(zaznaczony_wiersz.getTresc_dokumentu());

		wersja_dla_dokument_zakonczony(zaznaczony_wiersz);
	}

	private void wersja_dla_dokument_zakonczony(Obsluga_tableview zaznaczony_wiersz) {

		if (!(zaznaczony_wiersz.getData_zamkniecia() == null)) {
			nazwisko_kontr_TextField.setEditable(false);
			imie_kontr_TextField.setEditable(false);
			ulica_TextField.setEditable(false);
			nr_zam_TextField.setEditable(false);
			miasto_TextField.setEditable(false);
			kod_pocztowy_TextField.setEditable(false);
			email_TextField.setEditable(false);

			status_ComboBox.setVisible(false);
			TextField status_TextField = new TextField(status_ComboBox.getPromptText());
			status_TextField.setPrefWidth(192.0);
			status_TextField.prefHeight(25.0);
			status_TextField.setLayoutX(105.0);
			status_TextField.setLayoutY(267.0);
			anchorPane.getChildren().addAll(status_TextField);
			status_TextField.setEditable(false);

			przypisana_osoba_ComboBox.setVisible(false);
			TextField przypisana_osoba_TextField = new TextField(przypisana_osoba_ComboBox.getPromptText());
			przypisana_osoba_TextField.setPrefWidth(192.0);
			przypisana_osoba_TextField.prefHeight(25.0);
			przypisana_osoba_TextField.setLayoutX(433.0);
			przypisana_osoba_TextField.setLayoutY(267.0);
			anchorPane.getChildren().addAll(przypisana_osoba_TextField);
			przypisana_osoba_TextField.setEditable(false);

			data_dodania_DatePicker.setVisible(false);
			TextField data_dodania_TextField = new TextField(data_dodania_DatePicker.getValue().toString());
			data_dodania_TextField.setPrefWidth(192.0);
			data_dodania_TextField.prefHeight(25.0);
			data_dodania_TextField.setLayoutX(105.0);
			data_dodania_TextField.setLayoutY(307.0);
			anchorPane.getChildren().addAll(data_dodania_TextField);
			data_dodania_TextField.setEditable(false);

			data_zamkniecia_DatePicker.setVisible(false);
			TextField data_zamkniecia_TextField = new TextField(data_zamkniecia_DatePicker.getValue().toString());
			data_zamkniecia_TextField.setPrefWidth(192.0);
			data_zamkniecia_TextField.prefHeight(25.0);
			data_zamkniecia_TextField.setLayoutX(433.0);
			data_zamkniecia_TextField.setLayoutY(307.0);
			anchorPane.getChildren().addAll(data_zamkniecia_TextField);
			data_zamkniecia_TextField.setEditable(false);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AnchorPane.setTopAnchor(tresc_dokumentu_textArea, 376.0);
		AnchorPane.setLeftAnchor(tresc_dokumentu_textArea, 25.0);
		AnchorPane.setRightAnchor(tresc_dokumentu_textArea, 25.0);
		AnchorPane.setBottomAnchor(tab_hist_dok, 25.0);
		AnchorPane.setTopAnchor(linia1, 22.0);
		AnchorPane.setLeftAnchor(linia1, 151.0);
		AnchorPane.setRightAnchor(linia1, 10.0);
		AnchorPane.setTopAnchor(linia2, 239.0);
		AnchorPane.setLeftAnchor(linia2, 107.0);
		AnchorPane.setRightAnchor(linia2, 10.0);
		AnchorPane.setTopAnchor(linia3, 658.0);
		AnchorPane.setLeftAnchor(linia3, 155.0);
		AnchorPane.setRightAnchor(linia3, 10.0);

		status_ComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if (newValue.toString().trim().equals("zakończony - pozytywny")
					|| newValue.toString().trim().equals("zakończony - negatywny")) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Ostrzeżenie");
				alert.setHeaderText("Zmieniono status dokumentu na zakończony");
				alert.setContentText("Po zapisaniu danych nie będzie możliwa dalsza edycja dokumentu.");
				alert.showAndWait();
			}
		});

		tab_hist_data_oper
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("hist_data_operacji"));
		tab_hist_opis_oper
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("hist_opis_operacji"));
		tab_hist_dane_przed
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("hist_dane_przed_zmiana"));
		tab_hist_dane_po
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("hist_dane_po_zmianie"));
		tab_hist_uzytk.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("hist_uzytk"));

		try {
			przypisana_osoba_ComboBox.setItems(wyswietlenie_danych_zakladka_dokument.daj_liste_uzytkownikow(0));
			status_ComboBox.setItems(wyswietlenie_danych_zakladka_dokument.daj_liste_statusow(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
