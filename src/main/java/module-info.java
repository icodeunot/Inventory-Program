/**
 * @author John Salazar
 */
module jsalazar.inventory_c482 {
    requires javafx.controls;
    requires javafx.fxml;

    exports jsalazar.inventory_c482;
    opens jsalazar.inventory_c482 to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
}