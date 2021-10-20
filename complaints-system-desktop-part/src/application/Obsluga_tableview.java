package application;

import java.time.LocalDate;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Obsluga_tableview {

	public SimpleIntegerProperty numer = new SimpleIntegerProperty();
	public ReadOnlyObjectWrapper<LocalDate> data_dodania = new ReadOnlyObjectWrapper<LocalDate>();
	// public ObjectProperty<Date> data_dodania = new SimpleObjectProperty<Date>();
	public ReadOnlyStringWrapper status = new ReadOnlyStringWrapper();
	// public SimpleStringProperty status = new SimpleStringProperty();
	public ReadOnlyObjectWrapper<LocalDate> data_zamkniecia = new ReadOnlyObjectWrapper<LocalDate>();
	// public ObjectProperty<Date> data_zamkniecia = new
	// SimpleObjectProperty<Date>();
	public ReadOnlyStringWrapper przypisany_uzytkownik = new ReadOnlyStringWrapper();
	// public SimpleStringProperty przypisany_uzytkownik = new
	// SimpleStringProperty();
	public ReadOnlyStringWrapper nazwisko_kontrahenta = new ReadOnlyStringWrapper();
	// public SimpleStringProperty nazwisko_kontrahenta = new
	// SimpleStringProperty();
	public ReadOnlyStringWrapper imie_kontrahenta = new ReadOnlyStringWrapper();
	// public SimpleStringProperty imie_kontrahenta = new SimpleStringProperty();
	public ReadOnlyStringWrapper email = new ReadOnlyStringWrapper();
	public SimpleStringProperty tresc_dokumentu = new SimpleStringProperty();
	// public SimpleStringProperty email = new SimpleStringProperty();
	public ReadOnlyStringWrapper kod_pocztowy = new ReadOnlyStringWrapper();
	// public SimpleStringProperty kod_pocztowy = new SimpleStringProperty();
	public ReadOnlyStringWrapper miasto = new ReadOnlyStringWrapper();
	// public SimpleStringProperty miasto = new SimpleStringProperty();
	public ReadOnlyStringWrapper ulica = new ReadOnlyStringWrapper();
	// public SimpleStringProperty ulica = new SimpleStringProperty();
	public ReadOnlyStringWrapper nr_zam = new ReadOnlyStringWrapper();
	// public SimpleStringProperty nr_zam = new SimpleStringProperty();
	public ReadOnlyIntegerWrapper id_kontr = new ReadOnlyIntegerWrapper();
	public ReadOnlyIntegerWrapper id_rek = new ReadOnlyIntegerWrapper();
	public SimpleStringProperty hist_data_operacji = new SimpleStringProperty();
	public SimpleStringProperty hist_opis_operacji = new SimpleStringProperty();
	public SimpleStringProperty hist_dane_przed_zmiana = new SimpleStringProperty();
	public SimpleStringProperty hist_dane_po_zmianie = new SimpleStringProperty();
	public SimpleStringProperty hist_uzytk = new SimpleStringProperty();

	public String getHist_data_operacji() {
		return hist_data_operacji.get();
	}

//	public void setHist_data_operacji(String nowa_data) {
//		this.hist_data_operacji.set(nowa_data);
//	}

	public String getHist_opis_operacji() {
		return hist_opis_operacji.get();
	}

//	public void setHist_opis_operacji(String nowy_opis) {
//		this.hist_opis_operacji.set(nowy_opis);
//	}

	public String getHist_dane_przed_zmiana() {
		return hist_dane_przed_zmiana.get();
	}

//	public void setHist_dane_przed_zmiana(String nowe_dane_przed) {
//		this.hist_dane_przed_zmiana.set(nowe_dane_przed);
//	}

	public String getHist_dane_po_zmianie() {
		return hist_dane_po_zmianie.get();
	}

//	public void setHist_dane_po_zmianie(String nowe_dane_po) {
//		this.hist_dane_po_zmianie.set(nowe_dane_po);
//	}

	public String getHist_uzytk() {
		return hist_uzytk.get();
	}

//	public void setHist_uzytk(String nowy_uzytk) {
//		this.hist_uzytk.set(nowy_uzytk);
//	}

	public Integer getNumer() {
		return numer.get();
	}

	public Integer getId_kontr() {
		return id_kontr.get();
	}

	public Integer getId_rek() {
		return id_rek.get();
	}

	public String getStatus() {
		return status.get();
	}

	public String getPrzypisany_uzytkownik() {
		return przypisany_uzytkownik.get();
	}

	public String getNazwisko_kontrahenta() {
		return nazwisko_kontrahentaProperty().get();
	}

	public String getImie_kontrahenta() {
		return imie_kontrahenta.get();
	}

	public String getTresc_dokumentu() {
		return tresc_dokumentu.get();
	}
	
	public String getEmail() {
		return email.get();
	}

	public String getKod_pocztowy() {
		return kod_pocztowy.get();
	}

	public String getMiasto() {
		return miasto.get();
	}

	public String getUlica() {
		return ulica.get();
	}

	public String getNr_zam() {
		return nr_zam.get();
	}

	public LocalDate getData_dodania() {
		return data_dodania.get();
	}

	public LocalDate getData_zamkniecia() {
		return data_zamkniecia.get();
	}

	public void setNumer(Integer nowy_numer) {
		this.numer.set(nowy_numer);
	}

	public void setStatus(String nowy_status) {
		this.status.set(nowy_status);
	}

	public void setPrzypisany_uzytkownik(String nowy_przypisany_uzytkownik) {
		this.przypisany_uzytkownik.set(nowy_przypisany_uzytkownik);
	}

	public void setNazwisko_kontrahenta(String nowe_nazwisko_kontrahenta) {
		this.nazwisko_kontrahenta.set(nowe_nazwisko_kontrahenta);
	}

	public void setImie_kontrahenta(String nowe_imie_kontrahenta) {
		this.imie_kontrahenta.set(nowe_imie_kontrahenta);
	}

	public void setEmail(String nowy_email) {
		this.email.set(nowy_email);
	}

	public void setKod_pocztowy(String nowy_kod_pocztowy) {
		this.kod_pocztowy.set(nowy_kod_pocztowy);
	}

	public void setMiasto(String nowe_miasto) {
		this.miasto.set(nowe_miasto);
	}

	public void setUlica(String nowa_ulica) {
		this.ulica.set(nowa_ulica);
	}

	public void setNr_zam(String nowy_nr_zam) {
		this.nr_zam.set(nowy_nr_zam);
	}

	public void setData_dodania(LocalDate nowa_data_dodania) {
		this.data_dodania.set(nowa_data_dodania);
	}

	public void setData_zamkniecia(LocalDate nowa_data_zamkniecia) {
		this.data_zamkniecia.set(nowa_data_zamkniecia);
	}

	public ReadOnlyStringProperty nazwisko_kontrahentaProperty() {
		return nazwisko_kontrahenta.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty imie_kontrahentaProperty() {
		return imie_kontrahenta.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty ulicaProperty() {
		return ulica.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty nr_zamProperty() {
		return nr_zam.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty kod_pocztowyProperty() {
		return kod_pocztowy.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty miastoProperty() {
		return miasto.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty statusProperty() {
		return status.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty przypisany_uzytkownikProperty() {
		return przypisany_uzytkownik.getReadOnlyProperty();
	}

	public ReadOnlyObjectProperty<LocalDate> data_dodaniaProperty() {
		return data_dodania.getReadOnlyProperty();
	}

	public ReadOnlyObjectProperty<LocalDate> data_zamknieciaProperty() {
		return data_zamkniecia.getReadOnlyProperty();
	}
}
