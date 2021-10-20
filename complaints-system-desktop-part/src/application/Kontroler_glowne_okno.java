package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Kontroler_glowne_okno implements Initializable {
	@FXML
	private MenuItem nowy_dokument;
	@FXML
	private TabPane tabPane;
	@FXML
	private TableView<Obsluga_tableview> tableview;
	@FXML
	private TableColumn<Obsluga_tableview, Integer> tab_numer;
	@FXML
	private TableColumn<Obsluga_tableview, LocalDate> tab_data_dodania;
	@FXML
	private TableColumn<Obsluga_tableview, LocalDate> tab_data_zamkniecia;
	@FXML
	private TableColumn<Obsluga_tableview, String> tab_status, tab_przypisany_uzytkownik, tab_nazwisko_kontrahenta,
			tab_imie_kontrahenta, tab_kod_pocztowy, tab_miasto, tab_ulica, tab_nr_zam;
	@FXML
	private HBox hBox_przyciski_sel;
	@FXML
	private Button sel_wykonaj, wyczysc_sel;
	@FXML
	private TextField numer_dok_od_sel, numer_dok_do_sel, nazwisko_kontr_sel;
	@FXML
	private DatePicker data_dodania_od_sel, data_dodania_do_sel, data_zamkniecia_od_sel, data_zamkniecia_do_sel;
	@FXML
	private ComboBox<String> status_sel, przypisany_uzytkownik_sel;
	@FXML
	private Label zalog_uzytkownik;

	private ObservableList<Obsluga_tableview> kolekcja_danych_tableview = FXCollections.observableArrayList();

	@FXML
	private void czyszczenie_selektora(ActionEvent event) {
		numer_dok_od_sel.setText("");
		numer_dok_do_sel.setText("");
		nazwisko_kontr_sel.setText("");
		status_sel.setValue(null);
		przypisany_uzytkownik_sel.setValue(null);
		data_zamkniecia_od_sel.setValue(null);
		data_zamkniecia_do_sel.setValue(null);
		data_dodania_od_sel.setValue(null);
		data_dodania_do_sel.setValue(null);
	}

	@FXML
	private void menu_nowy_dokument(ActionEvent event_wlaczenie) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Nowy_dokument.fxml"));
		ScrollPane pane_with_fxml = (ScrollPane) loader.load();

		final Kontroler_nowy_dokument kontroler_dokument = loader.getController();

		Tab tab_dokument = new Tab();
		tab_dokument.setContent(pane_with_fxml);
		tab_dokument.setClosable(true);
		tab_dokument.setText("Nowy dokument");
		tabPane.getTabs().add(tab_dokument);
		tabPane.getSelectionModel().select(tab_dokument);

		tab_dokument.setOnCloseRequest(event -> {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Zapis danych");
			alert.setHeaderText("Dokument został zmodyfikowany");
			alert.setContentText("Czy chcesz zapisać zmiany?");

			ButtonType takButton = new ButtonType("Tak");
			ButtonType nieButton = new ButtonType("Nie");
			ButtonType anulujButton = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(takButton, nieButton, anulujButton);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == takButton) {
				try {
					kontroler_dokument.zapisz_zmiana_nowy_dokument(event);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (result.get() == nieButton) {
				// nic nie rób
			} else {
				event.consume();
			}

		});
	}

	@FXML
	private void wykonanie_selekcji_wyswietlenie_tableview(ActionEvent event) throws SQLException {

		tableview.getItems().clear();
		String query_selektor = "";

		if (!numer_dok_od_sel.getText().trim().isEmpty() && !numer_dok_do_sel.getText().trim().isEmpty())
			query_selektor += " AND id_rek BETWEEN " + Integer.parseInt(numer_dok_od_sel.getText().trim()) + " AND "
					+ Integer.parseInt(numer_dok_do_sel.getText().trim());

		if (!nazwisko_kontr_sel.getText().trim().isEmpty())
			query_selektor += " AND nazwisko_kontr LIKE '" + nazwisko_kontr_sel.getText().trim() + "%'";

		if (!(status_sel.getValue() == "wszystkie statusy" || status_sel.getValue() == null))
			query_selektor += " AND status = '" + status_sel.getValue().trim() + "'";

		if (!(przypisany_uzytkownik_sel.getValue() == "wszyscy użytkownicy"
				|| przypisany_uzytkownik_sel.getValue() == null))
			query_selektor += " AND imie_uzytk = '"
					+ przypisany_uzytkownik_sel.getValue().substring(0,
							przypisany_uzytkownik_sel.getValue().indexOf(" "))
					+ "'" + " AND nazwisko_uzytk = '"
					+ przypisany_uzytkownik_sel.getValue().substring(przypisany_uzytkownik_sel.getValue().indexOf(" "),
							przypisany_uzytkownik_sel.getValue().length()).trim()
					+ "'";

		if (!(data_zamkniecia_od_sel.getValue() == null) && !(data_zamkniecia_do_sel.getValue() == null))
			query_selektor += " AND data_zamkniecia BETWEEN '" + data_zamkniecia_od_sel.getValue() + "' AND '"
					+ data_zamkniecia_do_sel.getValue() + "'";

		if (!(data_dodania_od_sel.getValue() == null) && !(data_dodania_do_sel.getValue() == null))
			query_selektor += " AND data_dodania BETWEEN '" + data_dodania_od_sel.getValue() + "' AND '"
					+ data_dodania_do_sel.getValue() + "'";

		String tresc_zapytania = "SELECT d.id_rek, d.id_stat, d.id_uzytk, d.id_kontr, data_dodania, data_zamkniecia, "
				+ "k.imie_kontr, k.nazwisko_kontr, ulica, kod_pocztowy, miasto, mail, tresc_dokumentu, nr_zam, login, haslo, l.imie_uzytk, l.nazwisko_uzytk, "
				+ "status FROM dokument d, kontrahent k, logowanie l, status s "
				+ "WHERE d.id_kontr = k.id_kontr and d.id_stat = s.id_stat and d.id_uzytk = l.id_uzytk "
				+ query_selektor + ";";
		Polaczenie_z_baza przeslij_zapytanie = new Polaczenie_z_baza();
		ResultSet wynik_zapytania = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

		while (wynik_zapytania.next()) {
			Obsluga_tableview tview = new Obsluga_tableview();
			tview.kod_pocztowy.set(wynik_zapytania.getString("kod_pocztowy"));
			tview.miasto.set(wynik_zapytania.getString("miasto"));
			tview.ulica.set(wynik_zapytania.getString("ulica"));
			tview.nr_zam.set(wynik_zapytania.getString("nr_zam"));
			tview.nazwisko_kontrahenta.set(wynik_zapytania.getString("nazwisko_kontr"));
			tview.imie_kontrahenta.set(wynik_zapytania.getString("imie_kontr"));
			tview.przypisany_uzytkownik
					.set(wynik_zapytania.getString("imie_uzytk") + " " + wynik_zapytania.getString("nazwisko_uzytk"));
			tview.status.set(wynik_zapytania.getString("status"));
			tview.data_dodania.set(wynik_zapytania.getDate("data_dodania").toLocalDate());
			if (wynik_zapytania.getDate("data_zamkniecia") == null)
				;
			else
				tview.data_zamkniecia.set(wynik_zapytania.getDate("data_zamkniecia").toLocalDate());
			tview.numer.set(wynik_zapytania.getInt("id_rek"));
			tview.email.set(wynik_zapytania.getString("mail"));
			tview.id_kontr.set(wynik_zapytania.getInt("id_kontr"));
			tview.id_rek.set(wynik_zapytania.getInt("id_rek"));
			tview.tresc_dokumentu.set(wynik_zapytania.getString("tresc_dokumentu"));
			kolekcja_danych_tableview.add(tview);
		}
		tableview.setItems(kolekcja_danych_tableview);
	}

	private void otworz_zakladka_dokument(Obsluga_tableview zaznaczony_wiersz) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Zakladka_dokument.fxml"));
		ScrollPane pane_with_fxml = (ScrollPane) loader.load();

		final Kontroler_zakladka_dokument kontroler_dokument = loader.getController();

		Tab tab_dokument = new Tab();
		tab_dokument.setContent(pane_with_fxml);
		tab_dokument.setClosable(true);
		tab_dokument.setText(String.valueOf(zaznaczony_wiersz.getId_rek()) + "/"
				+ String.valueOf(zaznaczony_wiersz.getData_dodania()).substring(0, 4) + " "
				+ zaznaczony_wiersz.getNazwisko_kontrahenta());
		tabPane.getTabs().add(tab_dokument);

		kontroler_dokument.setZaznaczony_wiersz(zaznaczony_wiersz);

		tab_dokument.setOnCloseRequest(event -> {

			if (zaznaczony_wiersz.getData_zamkniecia() == null
					&& kontroler_dokument.pokazywanie_zapisywania(zaznaczony_wiersz)) {

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Zapis danych");
				alert.setHeaderText("Dokument został zmodyfikowany");
				alert.setContentText("Czy chcesz zapisać zmiany?");

				ButtonType takButton = new ButtonType("Tak");
				ButtonType nieButton = new ButtonType("Nie");
				ButtonType anulujButton = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(takButton, nieButton, anulujButton);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == takButton) {
					try {
						kontroler_dokument.zapisz_zmiana_zakladka_dokument(zaznaczony_wiersz);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (result.get() == nieButton) {
					// nic nie rób
				} else {
					event.consume();
				}
			}
		});
	}

	public boolean liczba_klikniec_myszy(MouseEvent double_click) {

		if (double_click.getButton().equals(MouseButton.PRIMARY)) {
			if (double_click.getClickCount() == 2)
				return true;
		}
		return false;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		zalog_uzytkownik.setText("Zalogowany użytkownik: " + Kontroler_logowanie.zalogowany_uzytkownik());

		tab_kod_pocztowy.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("kod_pocztowy"));
		tab_ulica.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("ulica"));
		tab_miasto.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("miasto"));
		tab_nr_zam.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("nr_zam"));
		tab_nazwisko_kontrahenta
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("nazwisko_kontrahenta"));
		tab_imie_kontrahenta
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("imie_kontrahenta"));
		tab_przypisany_uzytkownik
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("przypisany_uzytkownik"));
		tab_status.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, String>("status"));
		tab_data_dodania.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, LocalDate>("data_dodania"));
		tab_data_zamkniecia
				.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, LocalDate>("data_zamkniecia"));
		tab_numer.setCellValueFactory(new PropertyValueFactory<Obsluga_tableview, Integer>("numer"));

		AnchorPane.setBottomAnchor(hBox_przyciski_sel, 25.0);
		AnchorPane.setLeftAnchor(hBox_przyciski_sel, 24.0);

		tableview.setPlaceholder(new Label("Brak wyników. Skorzystaj z selektora, aby wyszukać dokumenty."));
		tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						if (mouseEvent.getClickCount() == 2) {
							try {
								otworz_zakladka_dokument(newValue);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			});
		});

		try {
			Wyswietlenie_danych_ComboBox wyswietlenie_danych_sel = new Wyswietlenie_danych_ComboBox();
			przypisany_uzytkownik_sel.setItems(wyswietlenie_danych_sel.daj_liste_uzytkownikow(1));
			status_sel.setItems(wyswietlenie_danych_sel.daj_liste_statusow(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
