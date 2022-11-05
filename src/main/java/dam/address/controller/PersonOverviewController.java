package dam.address.controller;

import dam.address.model.Person;
import dam.address.MainApp;
import dam.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

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

    /**
     * Método llamado cuando se hace clic en el botón 'delete'
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            //Mostramos la ventana emergente con el mensaje de confirmación para cerrar la aplicación:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            //Título de la ventana emergente para cerrar la aplicación:
            alert.setTitle("Delete person");
            //Quita el título cabecera de la ventana:
            alert.setHeaderText(null);
            //Mensaje a mostrar en la ventana emergente de la aplicación:
            alert.setContentText("Would you like to delete the selected person?");
            //Objeto para capturar el evento de si confirma el borrado de ventana o cancela:
            Optional<ButtonType> result = alert.showAndWait();
            //Si se ha pulsado el botón "Aceptar":
            if (result.get() == ButtonType.OK) {
                personTable.getItems().remove(selectedIndex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Noting selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }

    }

    /**
     * Método llamado cuando el usuario clica en el botón 'New'
     * Abre una ventana para editar los detalles de la nueva persona
     */
    @FXML
    private void handleNewPerson() {
        //Crea una nueva persona
        Person tempPerson = new Person();
        //Pasa esos datos al stage nuevo en el main
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        //Si se clicó 'OK' guarda y añade una nueva persona
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Método llamado cuando el usuario clica en el botón 'Edit'
     * y abre la ventana para editar los campos
     */
    @FXML
    private void handleEditPerson() {
        //Guarda los datos de la persona seleccionada
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        //Si no son nulos, se los pasa al stage de edición del main
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            //Si pulsó el botón 'OK' muestra los detalles de esa persona en la tabla
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // No se seleccionó persona
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

}