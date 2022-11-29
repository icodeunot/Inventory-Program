package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the ModifyProductController class. It is used to control the Modify Product Menu GUI.
 * @author John Salazar
 */
public class ModifyProductController implements Initializable {

     // Variables and Widgets

    /**
     * AdHoc list of parts
     */
    private ObservableList<Part> aPart = FXCollections.observableArrayList();
    @FXML
    public TextField modProdId;
    @FXML
    public TextField modProdName;
    @FXML
    public TextField modProdInv;
    @FXML
    public TextField modProdPrice;
    @FXML
    public TextField modProdMax;
    @FXML
    public TextField modProdMin;
    @FXML
    public TextField modProdSearchField;
    @FXML
    public Button modProdRemoveButton;
    @FXML
    public Button modProdAddButton;
    @FXML
    public Button modProdSave;
    @FXML
    public Button modProdCancel;

     // Add-Part Table View - "Modify Product" form

    @FXML
    public TableView<Part> addModProdTable;
    @FXML
    public TableColumn<Part, Integer> idColModAddProdTable;
    @FXML
    public TableColumn<Part, String> nameColModAddProdTable;
    @FXML
    public TableColumn<Part, Integer> inventoryColModAddProdTable;
    @FXML
    public TableColumn<Part, Double> priceColModAddProdTable;

     // Remove-Part Table View - "Modify Product" form

    @FXML
    public TableView<Part> removeModProdTable;
    @FXML
    public TableColumn<Part, Integer> idColModRemoveProdTable;
    @FXML
    public TableColumn<Part, String> nameColModRemoveProdTable;
    @FXML
    public TableColumn<Part, Integer> inventoryColModRemoveProdTable;
    @FXML
    public TableColumn<Part, Double> priceColModRemoveProdTable;

    /**
     * Populate and Assign the "Add" and "Remove" Part table views and columns on initialization
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtain parts associated with selected Product
        Product sProd = MainMenuController.getProduct();
        aPart = sProd.getAllAssociatedParts();
        // Populate "Add-Part" Table View
        idColModAddProdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColModAddProdTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColModAddProdTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColModAddProdTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        addModProdTable.setItems(Inventory.getAllParts());
        // Prepare "Remove-Part" Table View
        idColModRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColModRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColModRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColModRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        removeModProdTable.setItems(aPart);
        // Populate Product data
        modProdId.setText(String.valueOf(sProd.getId()));
        modProdName.setText(String.valueOf(sProd.getName()));
        modProdInv.setText(String.valueOf(sProd.getStock()));
        modProdPrice.setText(String.valueOf(sProd.getPrice()));
        modProdMax.setText(String.valueOf(sProd.getMax()));
        modProdMin.setText(String.valueOf(sProd.getMin()));
    }

     // "Modify Product" Form Event Handlers

    /**
     * Search-Parts function in "Modify-Product" Form: Uses modProdSearchField text to populate parts to add to product
     * @throws IOException
     */
    @FXML
    public void modProdSearchEvent() throws IOException {
        String userInput = modProdSearchField.getText();
        ObservableList<Part> results = Inventory.lookupPart(userInput);
        // Check by Name, if no results check by ID
        if (results.size() == 0) {
            try {
                int id = Integer.parseInt(userInput);
                Part p = Inventory.lookupPart(id);
                if (p != null) {
                    results.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }
        // Populate a Search Results table view
        addModProdTable.setItems(results);
        addModProdTable.refresh();
    }

    /**
     * Takes a selected part from the available inventory table and moves it to the final table view beneath
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modProductAddClicked(ActionEvent actionEvent) throws IOException {
        Part sPart = addModProdTable.getSelectionModel().getSelectedItem();
        // Verify a part is selected; add to the "Remove Part" Table View
        if (sPart == null) {
            alert(1);
        }
        else {
            aPart.add(sPart);
            removeModProdTable.setItems(aPart);
        }
    }

    /**
     * Removes a selected part from the "final" table before saving to product
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modProdRemoveClicked(ActionEvent actionEvent) throws IOException {
        Part sPart = removeModProdTable.getSelectionModel().getSelectedItem();
        // Verify a part is selected; add to the "Remove Part" Table View
        if (sPart == null) {
            alert(2);
        }
        else {
            alert(3);
        }
    }

    /**
     * Add the new product to the Inventory
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modProdSaveClicked(ActionEvent actionEvent) throws IOException {
        // Save Product data
        String name = modProdName.getText();
        double price = Double.parseDouble(modProdPrice.getText());
        int stock = Integer.parseInt(modProdInv.getText());
        int min = Integer.parseInt(modProdMin.getText());
        int max = Integer.parseInt(modProdMax.getText());
        // Validate min, max, inv
        if (min > max) {
            alert(5);
        }
        else if (stock < min || stock > max) {
            alert(6);
        }
        else {
            Product oldProd = MainMenuController.getProduct();
            int id = oldProd.getId();
            Inventory.deleteProduct(oldProd);
            // Call Product constructor, parse original product for associated parts and add to inventory
            Product newProd = new Product(id, name, price, stock, min, max);
            for (Part part : aPart) {
                newProd.addAssociatedPart(part);
            }
            Inventory.addProduct(newProd);
            // Return to main screen
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) modProdCancel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Cancel Button: Prompts user to confirm cancellation of modifying new product - Returns to Main Menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void modProdCancelClicked(ActionEvent actionEvent) throws IOException {
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
                alert.setHeaderText("NO PART SELECTED");
                alert.setContentText("Please select a part to add to the product.");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("NO PART SELECTED");
                alert.setContentText("Please select a part to remove from the product.");
                alert.showAndWait();
            }
            case 3 -> {
                Part sPart = removeModProdTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setContentText("Remove the " + sPart.getName() + " part from the product?");
                Optional<ButtonType> o = alert.showAndWait();
                if (o.isPresent() && o.get() == ButtonType.OK) {
                    aPart.remove(sPart);
                    removeModProdTable.setItems(aPart);
                }
            }
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Change of mind?");
                alert.setContentText("Do you want to cancel changes and return to the main menu?");
                Optional<ButtonType> o = alert.showAndWait();
                if (o.isPresent() && o.get() == ButtonType.OK) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) modProdCancel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("INVALID RANGE");
                alert.setContentText("Minimum can not exceed Maximum");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("INVALID RANGE");
                alert.setContentText("Inventory (Inv) can not be less than Minimum and can not exceed Maximum");
                alert.showAndWait();
            }
        }
    }
}
