package dam.address.controller;

import dam.address.MainApp;
import dam.address.model.Person;
import dam.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;

    private Stage dialogStage;
    private Person person;
    private MainApp mainApp;
    //booleana para controlar si se hace clic en el botón 'OK':
    private boolean okClicked = false;

    /**
     * Inicializa la clase con el constructor
     */
    @FXML
    private void initialize() {
    }

    /**
     * Establece el stage para esta ventana
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * 'pinta' los datos de la persona que será editada en esta ventana
     *
     * @param person en la que se hizo clic en el overview.
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Devolverá 'true' si el usuario hace clic en 'OK'; si no, devolverá false
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Método que se invoca al recibir el clic en 'OK'.
     * Si entra en el if (los datos son correctos) modifica los datos en el objeto
     * persona. Esos cambios son aplicados directamente sobre el objeto pasado
     * como argumento al método {@code setPerson(Person persona)}
     * */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }


    /**
     * Método llamado cuando el usuario hace clic en 'Cancel'
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    /**
     * Método para validar el input del usuario en los TextField
     *
     * @return true if el input es válido
     */
    private boolean isInputValid() {
        //variable para capturar el error, si lo hubiera
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().trim().length() == 0) {
            errorMessage += "No valid first name!\n";
        }

        if (lastNameField.getText() == null || lastNameField.getText().trim().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        if (streetField.getText() == null || streetField.getText().trim().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().trim().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // Control de excepciones para capturar que el usuario introducza un entero
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().trim().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().trim().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
        //Si la varaible de error está vacía, es que todos los datos son correctos
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Ventana emergente con el mensaje de error que corresponda
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct the invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }


}
