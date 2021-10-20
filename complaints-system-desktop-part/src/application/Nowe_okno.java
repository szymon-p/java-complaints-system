package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Nowe_okno extends Application {

	public void pokaz_okno(int symbol, int szerokosc, int wysokosc, String wygladFXML, String naglowek) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(wygladFXML));
			Scene scene = new Scene(root, szerokosc, wysokosc);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle(naglowek);
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("ikona.png")));
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
