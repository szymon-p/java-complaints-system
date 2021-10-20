package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Sprawdzanie_statusu
 */
@WebServlet("/Sprawdzanie_statusu")
public class Sprawdzanie_statusu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sprawdzanie_statusu() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String identyfikator = request.getParameter("identyfikator");
		String numer_reklamacji = identyfikator.substring(0, identyfikator.indexOf("/"));
		String rok_reklamacji = identyfikator.substring(identyfikator.indexOf("/")+1, identyfikator.length()).trim();

		String tresc_zapytania = "SELECT id_stat FROM dokument WHERE id_rek='" + numer_reklamacji
				+ "' AND data_dodania LIKE '" + rok_reklamacji + "%';";
		Integer id_stat = sprawdz_id_stat(tresc_zapytania);
		tresc_zapytania = "SELECT status FROM status WHERE id_stat='" + id_stat + "';";
		String status = sprawdz_nazwe_statusu(tresc_zapytania);	
		if (id_stat == 0) status = "w systemie nie ma takiego dokumentu";
		request.setAttribute("status", status);
		request.getRequestDispatcher("status.jsp").forward(request, response);

	}

	private ResultSet zapytanie_do_bazy(String tresc_zapytania) {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection polaczenie = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SOR_WEB",
					"123456");
			Statement wyrazenie = polaczenie.createStatement();
			ResultSet wynik_zapytania = wyrazenie.executeQuery(tresc_zapytania);
			polaczenie.close();
			return wynik_zapytania;
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private int sprawdz_id_stat(String tresc_zapytania) {
		int id_stat = 0;
		ResultSet wynik_zapytania_id_stat = zapytanie_do_bazy(tresc_zapytania);

		try {
			while (wynik_zapytania_id_stat.next()) {
				id_stat = wynik_zapytania_id_stat.getInt("id_stat");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return id_stat;
	}

	private String sprawdz_nazwe_statusu(String tresc_zapytania) {
		String nazwa_stat = "";
		ResultSet wynik_zapytania_nazwa_stat = zapytanie_do_bazy(tresc_zapytania);

		try {
			while (wynik_zapytania_nazwa_stat.next()) {
				nazwa_stat = wynik_zapytania_nazwa_stat.getString("status");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nazwa_stat;
	}
}
