module dam.address {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.xml.bind;


    opens dam.address to javafx.fxml;
    exports dam.address;
    exports dam.address.controller;
    exports dam.address.util;
    opens dam.address.controller to javafx.fxml;
    opens dam.address.model to java.xml.bind;
}