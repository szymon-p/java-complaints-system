package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/przesyl_danych")
public class Formularz_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Formularz_servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String imie = request.getParameter("imie");
		String nazwisko = request.getParameter("nazwisko");
		String email = request.getParameter("email");
		String ulica = request.getParameter("ulica");
		String nr_zam = request.getParameter("nr_zam");
		String miasto = request.getParameter("miasto");
		String kod_pocztowy = request.getParameter("kod_pocztowy");
		String tresc_rek = request.getParameter("tresc_rek");

		int count_kontr = sprawdz_lp_kontr();

		String tresc_zapytania = "INSERT INTO kontrahent (IMIE_KONTR, NAZWISKO_KONTR, ULICA, KOD_POCZTOWY, MIASTO, MAIL, NR_ZAM, ID_KONTR) VALUES"
				+ "('" + imie + "','" + nazwisko + "','" + ulica + "','" + kod_pocztowy + "','" + miasto + "','" + email
				+ "','" + nr_zam + "','" + count_kontr + "');";

		zapytanie_do_bazy(tresc_zapytania);

		int count_rek = sprawdz_lp_rek();

		tresc_zapytania = "INSERT INTO dokument (ID_REK, ID_STAT, ID_UZYTK, ID_KONTR, DATA_DODANIA, DATA_ZAMKNIECIA, TRESC_DOKUMENTU) VALUES"
				+ "('" + count_rek + "','1','1','" + count_kontr + "','" + LocalDate.now() + "',null,'" + tresc_rek
				+ "');";

		zapytanie_do_bazy(tresc_zapytania);

		tresc_zapytania = "INSERT INTO historia (OPIS_OPERACJI, DANE_PRZED_ZMIANA, DANE_PO_ZMIANIE, ID_REK, UZYTKOWNIK, DATA_OPERACJI) VALUES"
				+ "('Dodanie dokumentu','brak dokumentu','nowy dokument','" + count_rek + "','Klient','"
				+ pokaz_aktualna_date() + "');";
		zapytanie_do_bazy(tresc_zapytania);
		
		response.sendRedirect("wyslane.jsp");  
	}

	public String pokaz_aktualna_date() {
		Date aktualna_data = new Date();
		DateFormat formatowanie_daty = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		return formatowanie_daty.format(aktualna_data);
	}

	private ResultSet zapytanie_do_bazy(String tresc_zapytania) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection polaczenie = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost/xdb", "SOR_WEB", "123456");
			Statement wyrazenie = polaczenie.createStatement();
			ResultSet wynik_zapytania = wyrazenie.executeQuery(tresc_zapytania);
			polaczenie.close();
			return wynik_zapytania;
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private int sprawdz_lp_kontr() {
		int count = 0;
		String zapytanie_id_kontr = "SELECT COUNT(*) liczba_wystapien FROM kontrahent;";
		ResultSet wynik_zapytania_count = null;
		wynik_zapytania_count = zapytanie_do_bazy(zapytanie_id_kontr);

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

	private int sprawdz_lp_rek() {
		int count = 0;
		String zapytanie_id_rek = "SELECT COUNT(*) liczba_wystapien FROM dokument;";
		ResultSet wynik_zapytania_count = null;
		wynik_zapytania_count = zapytanie_do_bazy(zapytanie_id_rek);

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
}