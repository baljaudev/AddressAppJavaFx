package dam.address.controller;

import dam.address.model.Person;
import dam.address.MainApp;
import dam.address.util.DateUtil;
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
    @FXML
    private void initialize() {
        // Establece los datos que hemos creado y los pone en la columna:
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Establece 'persona' a null con lo que limpia los campos:
        showPersonDetails(null);

        // Obtenemos la selectedItemProperty de la tabla de personas y le añadimos un listener.
        //Mediante un lamba, llamamos al método que mostrará los datos del elemento seleccionado:
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
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

    /**
     * Rellena todos los campos de texto para mostrar los detalles de la persona
     * Si la Persona es null (en el else), limpia todos los campos
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Rellena las label con el texto que coja de la instancia de Person:
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Si la persona es null, vacía todos los campos:
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

}