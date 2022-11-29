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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the ModifyPartController Class. It is used to control the Modify Part GUI.
 * @author John Salazar<br><br>
 *
 * <b>LOGICAL ERROR - Rubric Item B</b><br>
 * When developing the ModifyPartController and FXML, I simply saved the AddPart.FXML as ModifyPart.
 * FXML and updated the FX:ID prefix values from "add" to "mod".<br>
 * I missed a prefix value update and spent a lot of time trying to figure out why the Part Name was appearing as Null.<br>
 * The FXID was valid, but referring to the "add" version as opposed to the "mod" version of the attribute, and was indeed null.<br>
 * Line 44, 87, 111 and 148 were all affected. Line 111 and 187 caused Alerts with null values.
 */
public class ModifyPartController implements Initializable {

     // Variables and Widgets

    @FXML
    public RadioButton modInhouseRadio;
    @FXML
    public RadioButton modOutsourcedRadio;
    @FXML
    public ToggleGroup partTypeModify;
    @FXML
    public TextField modPartId;
    @FXML
    public TextField modPartName;
    @FXML
    public TextField modPartInv;
    @FXML
    public TextField modPartPrice;
    @FXML
    public TextField modPartMax;
    @FXML
    public TextField modPartMin;
    @FXML
    public Label modPartTypeLabel;
    @FXML
    public TextField modPartTypeField;
    @FXML
    public Button modPartCancel;
    @FXML
    public Button modPartSave;

    /**
     * Initializer - Populates the Modify Part form with the selected Part's data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verify a part is selected and determine In-House or Outsourced
        Part sPart = controller.MainMenuController.getPart();
        if (sPart instanceof InHouse) {
            modInhouseRadio.setSelected(true);
            modPartTypeLabel.setText("Machine ID");
            modPartTypeField.setText(String.valueOf(((InHouse) sPart).getMachineId()));
        } else if (sPart instanceof Outsourced) {
            modOutsourcedRadio.setSelected(true);
            modPartTypeLabel.setText("Company Name");
            modPartTypeField.setText(String.valueOf(((Outsourced) sPart).getCompanyName()));
        }
        else {
            try {
                alert(5);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Populate the text fields in the Modify Part form
        modPartId.setText(String.valueOf(sPart.getId()));
        modPartName.setText(String.valueOf(sPart.getName()));
        modPartInv.setText(String.valueOf(sPart.getStock()));
        modPartPrice.setText(String.valueOf(sPart.getPrice()));
        modPartMax.setText(String.valueOf(sPart.getMax()));
        modPartMin.setText(String.valueOf(sPart.getMin()));
    }

    // Modify Part Form Event Handlers

    /**
     * In-House Radio Button: selected - Requires MachineId as int
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modInhouseSelected(ActionEvent actionEvent) throws IOException {
        modPartTypeLabel.setText("Machine ID");
    }

    /**
     * Outsourced Radio Button: selected - Requires companyName as String
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modOutsourcedSelected(ActionEvent actionEvent) throws IOException {
        modPartTypeLabel.setText("Company Name");
    }

    /**
     * Save Button: Verify part changes and update part in inventory. Checks for in-house or outsourced, validates data type
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modPartSaveClicked(ActionEvent actionEvent) throws IOException {
        if (modInhouseRadio.isSelected()) {
            String name = modPartName.getText();
            try { // Capture the correct data for In-House Part
                double price = Double.parseDouble(modPartPrice.getText());
                int stock = Integer.parseInt(modPartInv.getText());
                int min = Integer.parseInt(modPartMin.getText());
                int max = Integer.parseInt(modPartMax.getText());
                int machineId = Integer.parseInt(modPartTypeField.getText());

                // Validate min, max, inv
                if (min > max) {
                    alert(2);
                }
                else if (stock < min || stock > max) {
                    alert(3);
                }
                else {
                    // Obtain original Part ID and delete from Inventory
                    Part oldPart = MainMenuController.getPart();
                    int oldId = oldPart.getId();
                    Inventory.deletePart(oldPart);
                    // Call InHouse constructor and add to inventory
                    InHouse moddedPart = new InHouse(oldId, name, price, stock, min, max, machineId);
                    Inventory.addPart(moddedPart);
                    // Return to main screen
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) modPartCancel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            catch (Exception e) {
                alert(1);
            }
        }
        else {
            try { // Capture the correct data for Outsourced Part
                String name = modPartName.getText();
                double price = Double.parseDouble(modPartPrice.getText());
                int stock = Integer.parseInt(modPartInv.getText());
                int min = Integer.parseInt(modPartMin.getText());
                int max = Integer.parseInt(modPartMax.getText());
                String companyName = modPartTypeField.getText();

                // Validate Min, Max, Inventory
                if (min > max) {
                    alert(2);
                } else if (stock < min || stock > max) {
                    alert(3);
                }
                else {
                    // Obtain original Part ID and delete from Inventory
                    Part oldPart = MainMenuController.getPart();
                    int oldId = oldPart.getId();
                    Inventory.deletePart(oldPart);

                    // Call the Outsourced Constructor and add to inventory
                    Outsourced moddedPart = new Outsourced(oldId, name, price, stock, min, max, companyName);
                    Inventory.addPart(moddedPart);
                    // Return to main screen
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) modPartCancel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            catch (Exception e) {
                alert(1);
            }
        }
    }

    /**
     * Cancel Button: Prompts user to confirm cancellation of updating existing part - Returns to Main Menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modPartCancelClicked(ActionEvent actionEvent) throws IOException {
        alert(4);
    }

     // Alerts and Confirmations

    /**
     * Alert method. Used to warn user of errors.
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
                    Stage stage = (Stage) modPartCancel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ruh-roh");
                alert.setHeaderText("Houston...");
                alert.setContentText("We have a problem.");
                alert.showAndWait();
            }
        }
    }

}
