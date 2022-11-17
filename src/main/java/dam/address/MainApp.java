package dam.address;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import dam.address.controller.PersonEditDialogController;
import dam.address.controller.PersonOverviewController;
import dam.address.controller.RootLayoutController;
import dam.address.model.Person;
import dam.address.model.PersonListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.prefs.Preferences;

public class MainApp extends Application {
    private static final String LOGO = "file:src/main/resources/dam/address/images/AddressIcon.png";
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
        // Establecer el icono de la aplicación:
        this.primaryStage.getIcons().add(new Image(LOGO));

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

            // Le da al controlador acceso a la clase ejecutable
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
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

    /**
     * Abre el stage de edición con los datos de la persona seleccionada.
     * Si el usuario hace clic en 'OK', los cambios se guardan en dicha persona
     *
     * @param person el objeto persona que será editado
     * @return true si el usuario hizo clic en 'OK'
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Carga del fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Creación del stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Asigna el controlador
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            // Le pasa la persona seleccionada al controlador
            controller.setPerson(person);
            // Muestra la ventana y espera hasta que el usuario la cierre
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Actualiza el título de la aplicación.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
        }
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Guarda los datos de personas actuales al archivo especificado.
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            //Instancia de un jaxb para que coja la estructura que hemos establecido
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            // Instancia de un marshaller para meter datos en el XML
            Marshaller m = context.createMarshaller();
            // Configuramos el marshaller para meter datos en el XML
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Utilizamos el envoltorio para establecer dónde guardar los datos
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Escribimos los datos XML en el fichero
            m.marshal(wrapper, file);

            // Guarda la ruta del archivo
            setPersonFilePath(file);

        } catch (Exception e) {
            e.printStackTrace();// captura cualquier excepción
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Could not save data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    /**
     * Carga los datos de las personas del archivo XML especificado.
     * Los datos actuales serán reemplazados, en caso de haberlos.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            //Instancia de un jaxb para que coja la estructura que hemos establecido
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            // Instancia de un marshaller para sacar datos en el XML
            Unmarshaller um = context.createUnmarshaller();

            // Utilizamos el envoltorio para establecer dónde sacar los datos
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            //Limpia la tabla
            personData.clear();
            // Y añade esos datos recuperados en la tabla
            personData.addAll(wrapper.getPersons());

            // Guarda la ruta del archivo
            setPersonFilePath(file);

        } catch (Exception e) {
            e.printStackTrace();// captura cualquier excepción
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }

    }



    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}