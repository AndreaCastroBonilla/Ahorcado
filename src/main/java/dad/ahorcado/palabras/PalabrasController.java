package dad.ahorcado.palabras;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

public class PalabrasController implements Initializable {

	// model

	private ListProperty<String> palabras = new SimpleListProperty<>(FXCollections.observableArrayList());

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Button nuevoButton, quitarButton;

	@FXML
	private ListView<String> palabrasList;

	public PalabrasController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabrasView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		palabrasList.itemsProperty().bind(palabras);

	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onNuevoAction(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("nueva palabra");
		dialog.setTitle("Añadir palabra");
		dialog.setHeaderText("¡Vas a añadir una palabra nueva!");
		dialog.setContentText("Por favor, escríbela: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			palabras.add(result.get().toUpperCase());
		}

	}

	@FXML
	void onQuitarAction(ActionEvent event) {

		palabras.remove(palabras.size() - 1);
	}

	public final ListProperty<String> palabrasProperty() {
		return this.palabras;
	}

	public final ObservableList<String> getPalabras() {
		return this.palabrasProperty().get();
	}

	public final void setPalabras(final ObservableList<String> palabras) {
		this.palabrasProperty().set(palabras);
	}

}
