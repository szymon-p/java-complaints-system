package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Polaczenie_z_baza {

	public ResultSet zapytanie_do_bazy(String tresc_zapytania) throws SQLException {
		Connection polaczenie = DriverManager
				.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
		Statement wyrazenie = polaczenie.createStatement();
		ResultSet wynik_zapytania = wyrazenie.executeQuery(tresc_zapytania);
		polaczenie.close();
		return wynik_zapytania;

	}
}
