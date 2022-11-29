package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * This is the AddPartController Class. It is used to control the "Add Parts" menu.
 * @author John Salazar
 */
public class AddPartController implements Initializable {

    // Variables and Widgets

    @FXML
    public RadioButton addInhouseRadio;
    @FXML
    public RadioButton addOutsourcedRadio;
    @FXML
    public ToggleGroup partType;
    @FXML
    public TextField addPartId;
    @FXML
    public TextField addPartName;
    @FXML
    public TextField addPartInv;
    @FXML
    public TextField addPartPrice;
    @FXML
    public TextField addPartMax;
    @FXML
    public TextField addPartMin;
    @FXML
    public Label addPartTypeLabel;
    @FXML
    public TextField addPartTypeField;
    @FXML
    public Button addPartSave;
    @FXML
    public Button addPartCancel;
    /**
     * Initialize method.<br>
     * No action on initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

     // Add Part Form Event Handlers

    /**
     * In-House Radio Button: selected - Requires MachineId as int
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void inHouseSelected(ActionEvent actionEvent) throws IOException {
        addPartTypeLabel.setText("Machine ID");
    }

    /**
     * Outsourced Radio Button: selected - Requires companyName as String
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void outsourcedSelected(ActionEvent actionEvent) throws IOException {
        addPartTypeLabel.setText("Company Name");
    }

    /**
     * Save Button: Verify new part attributes and add new part to inventory. Checks for in-house or outsourced, validates data type
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addPartSaveClicked(ActionEvent actionEvent) throws IOException {
        if (addInhouseRadio.isSelected()) {
            try { // Capture the right data
                int id = Inventory.createId();
                String name = addPartName.getText();
                double price = Double.parseDouble(addPartPrice.getText());
                int stock = Integer.parseInt(addPartInv.getText());
                int min = Integer.parseInt(addPartMin.getText());
                int max = Integer.parseInt(addPartMax.getText());
                int machineId = Integer.parseInt(addPartTypeField.getText());
                // Validate min, max, inv
                if (min > max) {
                    alert(2);
                } else if (stock < min || stock > max) {
                    alert(3);
                }
                // Call InHouse constructor and add to inventory
                InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(newPart);
                // Return to main screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) addPartCancel.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e) {
                alert(1);
            }
        }
        else {
            try {
                int id = Inventory.createId();
                String name = addPartName.getText();
                double price = Double.parseDouble(addPartPrice.getText());
                int stock = Integer.parseInt(addPartInv.getText());
                int min = Integer.parseInt(addPartMin.getText());
                int max = Integer.parseInt(addPartMax.getText());
                String companyName = addPartTypeField.getText();
                // Validate Min, Max, Inventory
                if (min > max) {
                    alert(2);
                } else if (stock < min || stock > max) {
                    alert(3);
                }
                // Call the Outsourced Constructor and add to inventory
                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(newPart);
                // Return to main screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) addPartCancel.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e) {
                alert(1);
            }
        }
    }

    /**
     * Cancel Button: Prompts user to confirm cancellation of adding new part - Returns to Main Menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addPartCancelClicked(ActionEvent actionEvent) throws IOException {
        alert(4);
    }

    // Alerts and Confirmations

    /**
     * Alert method to dialog errors to user.
     * @param alertNum
     * @throws IOException
     */
    private void alert(int alertNum) throws IOException {
        switch (alertNum) {
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("INVALID INPUT");
                alert.setContentText("Please review the form for errors and re-submit");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("INVALID RANGE");
                alert.setContentText("Minimum can not exceed Maximum");
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("INVALID RANGE");
                alert.setContentText("Inventory (Inv) can not be less than Minimum and can not exceed Maximum");
                alert.showAndWait();
            }
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Change of mind?");
                alert.setContentText("Do you want to cancel changes and return to the main menu?");
                Optional<ButtonType> o = alert.showAndWait();
                if (o.isPresent() && o.get() == ButtonType.OK) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) addPartCancel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }
}
