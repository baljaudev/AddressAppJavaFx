package dam.address;

import java.io.IOException;

import dam.address.controller.PersonOverviewController;
import dam.address.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    //Instancia de objeto ObservaleList para manejar los datos y añadirlos
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Datos de ejemplo:
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    //Retorna estos datos de ejemplo
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Inicializa el layout principal de la aplicación.
     */
    public void initRootLayout() {
        try {
            // Cargamos el layout raíz desde el fichero fxml:
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            //Muestra la escena que contiene el layout raíz:
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra el fichero FXML dentro del layout raíz.
     */
    public void showPersonOverview() {
        try {
            // Carga el fichero fxml que le pasemos por parámetro. Debemos referirnos al Pane que tiene:
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            //Establece el fxml de PersonOverview en el centro del layout raíz:
            rootLayout.setCenter(personOverview);
            // Le indica a la clase ejecutable, cuál es su controlador:
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}