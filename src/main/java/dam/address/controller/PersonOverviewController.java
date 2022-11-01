package dam.address.controller;

import dam.address.model.Person;
import dam.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
    private MainApp mainApp;
    //Instancias de los nodos que vamos a manejar en el controlador:
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    public PersonOverviewController() {
    }

    /**
     * Inicializa la clase controladora. Este método se llama una vez se carga el fxml
     */
    /**
     * Inicializa la clase controladora. Este método se llama una vez se carga el fxml
     */
    @FXML
    private void initialize() {
        // Establece los datos que hemos creado y los pone en la columna:
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    /**
     * Le indica al controlador cuál es la clase ejecutable.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 'Pinta' los datos en la tabla
        personTable.setItems(mainApp.getPersonData());
    }
}