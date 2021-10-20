package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Wyswietlenie_danych_ComboBox {

	private ObservableList<String> kolekcja_danych_uzytkownik = FXCollections.observableArrayList();
	private ObservableList<String> kolekcja_danych_status = FXCollections.observableArrayList();
	private int count;
	private String tresc_zapytania;

	public String[][] wyswietlenie_danych_status() throws SQLException {
		Polaczenie_z_baza przeslij_zapytanie = new Polaczenie_z_baza();
		tresc_zapytania = "SELECT COUNT(*) liczba_wystapien FROM status;";
		ResultSet wynik_zapytania_count = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		while (wynik_zapytania_count.next()) {
			count = wynik_zapytania_count.getInt("liczba_wystapien");
		}

		String[][] tablica_statusow = new String[2][count];

		tresc_zapytania = "SELECT * FROM status;";
		ResultSet wynik_zapytania = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

		for (int i = 0; wynik_zapytania.next(); i++) {
			// 0 - id_stat, 1 - status
			tablica_statusow[0][i] = String.valueOf(wynik_zapytania.getInt("id_stat"));
			tablica_statusow[1][i] = wynik_zapytania.getString("status");
		}
		return tablica_statusow;
	}

	public int daj_id_stat_statusu(String status) throws SQLException {
		String[][] tablica_statusow = wyswietlenie_danych_status();

		for (int i = 0; i < count; i++) {
			if (tablica_statusow[1][i].equals(status))
				return Integer.parseInt(tablica_statusow[0][i]);
		}
		return 0;
	}

	public ObservableList<String> daj_liste_statusow(Integer symbol) throws SQLException {
		String[][] tablica_statusow = wyswietlenie_danych_status();
		for (int i = 0; i < count; i++) {
			kolekcja_danych_status.add(tablica_statusow[1][i]);
		}

		if (symbol == 1)
			kolekcja_danych_status.add("wszystkie statusy");

		if (symbol == -1) {
			kolekcja_danych_status.remove("zakończony - pozytywny");
			kolekcja_danych_status.remove("zakończony - negatywny");
		}

		return kolekcja_danych_status;
	}

	public String[][] wyswietlenie_danych_uzytkownik() throws SQLException {

		Polaczenie_z_baza przeslij_zapytanie = new Polaczenie_z_baza();
		tresc_zapytania = "SELECT COUNT(*) liczba_wystapien FROM logowanie;";
		ResultSet wynik_zapytania_count = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);
		while (wynik_zapytania_count.next()) {
			count = wynik_zapytania_count.getInt("liczba_wystapien");
		}

		String[][] tablica_uzytkownikow = new String[2][count];

		tresc_zapytania = "SELECT * FROM logowanie;";
		ResultSet wynik_zapytania = przeslij_zapytanie.zapytanie_do_bazy(tresc_zapytania);

		for (int i = 0; wynik_zapytania.next(); i++) {
			// 0 - id_uzytk, 1 - imie i nazwisko
			tablica_uzytkownikow[0][i] = String.valueOf(wynik_zapytania.getInt("id_uzytk"));
			tablica_uzytkownikow[1][i] = wynik_zapytania.getString("imie_uzytk") + " "
					+ wynik_zapytania.getString("nazwisko_uzytk");
		}
		return tablica_uzytkownikow;
	}

	public int daj_id_uzytk_uzytkownika(String uzytkownik) throws SQLException {
		String[][] tablica_uzytkownikow = wyswietlenie_danych_uzytkownik();
		for (int i = 0; i < count; i++) {
			if (tablica_uzytkownikow[1][i].equals(uzytkownik))
				return Integer.parseInt(tablica_uzytkownikow[0][i]);
		}
		return 0;
	}

	public ObservableList<String> daj_liste_uzytkownikow(Integer symbol) throws SQLException {
		String[][] tablica_uzytkownikow = wyswietlenie_danych_uzytkownik();
		for (int i = 0; i < count; i++) {
			kolekcja_danych_uzytkownik.add(tablica_uzytkownikow[1][i]);
		}

		if (symbol == 1)
			kolekcja_danych_uzytkownik.add("wszyscy użytkownicy");

		return kolekcja_danych_uzytkownik;
	}

}
