module com.example.agenda {
    requires javafx.controls;
    requires javafx.fxml;


    opens dam.address to javafx.fxml;
    exports dam.address;
    exports dam.address.controller;
    opens dam.address.controller to javafx.fxml;
}