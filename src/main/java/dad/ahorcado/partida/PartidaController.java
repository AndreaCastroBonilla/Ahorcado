package dad.ahorcado.partida;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import dad.ahorcado.puntuaciones.Puntuacion;
import dad.ahorcado.puntuaciones.PuntuacionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PartidaController implements Initializable {

	// model

	private StringProperty letraProperty = new SimpleStringProperty();

	private String palabra; // palabra a adivinar
	private char[] guiones; // guiones de la palabra
	private Puntuacion puntuacion;
	private int puntos = 0;
	private int intentos = 9;

	// view

	@FXML
	private Button resolverButton, letraButton;

	@FXML
	private Label casillasLabel, letrasUsadasLabel, puntosLabel;

	@FXML
	private ImageView imageView;

	@FXML
	private TextField letraField;

	@FXML
	private GridPane view;

	public PartidaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		letraProperty.bind(letraField.textProperty());

		letraButton.setOnAction(this::onComprobarLetraAction);
		resolverButton.setOnAction(this::onResolverAction);

		// load data

		palabra = cargarPalabra();

	}

	@FXML
	public void onComprobarLetraAction(ActionEvent event) {

		if (faltanLetras(guiones)) {
			if (comprobarLetra(palabra, guiones)) {
				casillasLabel.setText("");
				for (int j = 0; j < guiones.length; j++) {
					casillasLabel.setText(casillasLabel.getText() + " " + guiones[j]);
				}

				puntos++;
				puntosLabel.setText(String.valueOf(puntos));

			} else {

				intentos--;
				loadImages(intentos);

			}
		} else {
			TextInputDialog dialog = new TextInputDialog("player name");
			dialog.setTitle("Añadir jugador");
			dialog.setHeaderText("YOU´RE A WINNER");
			dialog.setContentText("Escribe tu nombre: ");
			dialog.setGraphic(new ImageView(this.getClass().getResource("/images/winner1.png").toString()));

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				puntuacion = new Puntuacion(result.get(), puntos);
				PuntuacionController.puntuaciones.add(puntuacion);
			}

		}

		if (intentos == 1) {
			TextInputDialog dialog = new TextInputDialog("player name");
			dialog.setTitle("Añadir jugador");
			dialog.setHeaderText("GAME OVER :(");
			dialog.setContentText("Escribe tu nombre: ");
			dialog.setGraphic(new ImageView(this.getClass().getResource("/images/loser1.png").toString()));

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				puntuacion = new Puntuacion(result.get(), puntos);
				PuntuacionController.puntuaciones.add(puntuacion);
			}
		}

	}

	private boolean faltanLetras(char[] g) {
		for (int i = 0; i < g.length; i++) {
			if (g[i] == '_') {
				return true;
			}
		}
		return false;
	}

	private void loadImages(int intentos) {
		Image image;
		switch (intentos) {
		case 8:
			image = new Image(getClass().getResourceAsStream("/images/2.png"));
			imageView.setImage(image);
			break;
		case 7:
			image = new Image(getClass().getResourceAsStream("/images/3.png"));
			imageView.setImage(image);
			break;
		case 6:
			image = new Image(getClass().getResourceAsStream("/images/4.png"));
			imageView.setImage(image);
			break;
		case 5:
			image = new Image(getClass().getResourceAsStream("/images/5.png"));
			imageView.setImage(image);
			break;
		case 4:
			image = new Image(getClass().getResourceAsStream("/images/6.png"));
			imageView.setImage(image);
			break;
		case 3:
			image = new Image(getClass().getResourceAsStream("/images/7.png"));
			imageView.setImage(image);
			break;
		case 2:
			image = new Image(getClass().getResourceAsStream("/images/8.png"));
			imageView.setImage(image);
			break;
		case 1:
			image = new Image(getClass().getResourceAsStream("/images/9.png"));
			imageView.setImage(image);
			break;
		}
	}

	private boolean comprobarLetra(String p, char[] g) {
		boolean iguales = false;
		char aux = 0;
		String str = null;

		for (int i = 0; i < p.length(); i++) {
			str = letraField.getText().toUpperCase();
			if (p.charAt(i) == str.charAt(0)) {

				g[i] = p.charAt(i);
				iguales = true;

			} else {
				aux = letraField.getText().charAt(0);
				str = String.valueOf(aux).toUpperCase();
			}
		}

		letrasUsadasLabel.setText(letrasUsadasLabel.getText() + " " + str);
		letraField.clear();
		return iguales;
	}

	private char[] cargarGuiones(String palabra) {
		// guardamos en un array el tamaño de dicha palabra y lo mostramos en el label
		guiones = new char[palabra.length()];
		for (int i = 0; i < guiones.length; i++) {
			guiones[i] = '_';
		}
		casillasLabel.setText(String.valueOf(guiones));
		return guiones;
	}

	private String cargarPalabra() {
		Random r = new Random();
		List<String> file = null;
		String palabra = null;

		try {

			// cargar y leer fichero
			file = Files.readAllLines(Paths.get("palabras.txt"));

			// obtener un número aleatorio a partir del tamaño del fichero
			int numAle = r.nextInt(file.size());

			// asignamos una palabra aleatoria
			palabra = file.get(numAle);
			cargarGuiones(palabra);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return palabra;
	}

	@FXML
	void onResolverAction(ActionEvent event) {

		casillasLabel.setText(palabra);

	}

	public GridPane getView() {
		return view;
	}

}
