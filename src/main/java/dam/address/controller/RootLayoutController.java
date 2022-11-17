package dam.address.controller;

import dam.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Crea una agenda vacía
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Abre un FileChooser que permite al usuario seleccionar un archivo XML para cargarlo.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Acota la extensión que permite usarse para el archivo
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra la ventana de abrir archivo
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Guarda el archivo que está ahora abierto y, si no hay archivo, muestra el
     * diálogo de guardar como.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Abre un FileChooser para permitir al usuario elegir la ruta de guardar como.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Establece el filtro para la extensión XML
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra el dialog de guardado
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Nos aseguramos de que el fichero es XML
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Abre un Alert con el aviso de la información de la aplicación.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("About the app");
        alert.setHeaderText(null);
        alert.setContentText("Author: Marco Jakob" +
                "\nWebsite: http://code.makery.ch" +
                "\nAutor of this version: @baljaudev");
        alert.showAndWait();
    }

    /**
     * Cierra la aplicación
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
