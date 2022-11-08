package dad.ahorcado;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import dad.ahorcado.puntuaciones.Puntuacion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AhorcadoApp extends Application {

	private static final File PALABRAS_FILE = new File("palabras.txt");
	private static final File PUNTUACIONES_FILE = new File("puntuaciones.txt");

	public static Stage primaryStage;
	private RootController rootController = new RootController();;

	@Override
	public void init() throws Exception {
		System.out.println("Inicializando");
		if (PALABRAS_FILE.exists()) {
			rootController.getPalabras().addAll(Files.readAllLines(PALABRAS_FILE.toPath(), StandardCharsets.UTF_8));
		}

		if (PUNTUACIONES_FILE.exists()) {
			FileWriter fw = new FileWriter(PUNTUACIONES_FILE);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < rootController.getPuntuaciones().size(); i++) {
				bw.write(i);
			}
			bw.close();

		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		AhorcadoApp.primaryStage = primaryStage;

		primaryStage.setTitle("Ahorcado");
		primaryStage.setScene(new Scene(rootController.getView()));
		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		System.out.println("Terminando");

		String contenido = "";
		for (String palabra : rootController.getPalabras()) {
			contenido += palabra + "\n";
		}
		Files.writeString(PALABRAS_FILE.toPath(), contenido.trim(), StandardCharsets.UTF_8, StandardOpenOption.CREATE);

		String contenido2 = "";
		for (Puntuacion puntuacion : rootController.getPuntuaciones()) {
			contenido2 += puntuacion + "\n";
		}
		Files.writeString(PUNTUACIONES_FILE.toPath(), contenido2.trim(), StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
