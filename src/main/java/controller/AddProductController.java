package controller;

import javafx.application.Platform;
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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the AddProductController class. It is used to control the "Add Product" menu.
 * @author John Salazar
 */
public class AddProductController implements Initializable {

     // Variables and Widgets

    /**
     * List of ad-hoc parts.
     */
    private final ObservableList<Part> aPart = FXCollections.observableArrayList();
    @FXML
    public TextField addProdId;
    @FXML
    public TextField addProdName;
    @FXML
    public TextField addProdInv;
    @FXML
    public TextField addProdPrice;
    @FXML
    public TextField addProdMax;
    @FXML
    public TextField addProdMin;
    @FXML
    public TextField addProdSearchField;
    @FXML
    public Button addProdAddButton;
    @FXML
    public Button addProdRemoveButton;
    @FXML
    public Button addProdSave;
    @FXML
    public Button addProdCancel;

     // Add-Part Table View - "Add Product" form

    @FXML
    public TableView<Part> addProdTable;
    @FXML
    public TableColumn<Part, Integer> idColAddProdTable;
    @FXML
    public TableColumn<Part, String> nameColAddProdTable;
    @FXML
    public TableColumn<Part, Integer> inventoryColAddProdTable;
    @FXML
    public TableColumn<Part, Double> priceColAddProdTable;

     // Remove-Part Table View - "Add Product" form

    @FXML
    public TableView<Part> removeProdTable;
    @FXML
    public TableColumn<Part, Integer> idColRemoveProdTable;
    @FXML
    public TableColumn<Part, String> nameColRemoveProdTable;
    @FXML
    public TableColumn<Part, Integer> inventoryColRemoveProdTable;
    @FXML
    public TableColumn<Part, Double> priceColRemoveProdTable;

    /**
     * Populate and Assign the "Add" and "Remove" Part table views and columns on initialization
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start cursor in the search field
        addProdSearchField.setFocusTraversable(false);
        // Populate "Add-Part" Table View
        addProdTable.setItems(Inventory.getAllParts());
        idColAddProdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColAddProdTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColAddProdTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColAddProdTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Assign "Remove-Part" Table View
        idColRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColRemoveProdTable.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

     // "Add Product" Form Event Handlers

    /**
     * Search-Parts function in "Add-Product" Form: Uses addProdSearchField text to populate parts to add to product
     * @throws IOException
     */
    @FXML
    public void addProdSearchEvent() throws IOException {
        String userInput = addProdSearchField.getText();
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
        addProdTable.setItems(results);
        addProdTable.refresh();
    }

    /**
     * Takes a selected part from the available inventory table and moves it to the final table view beneath
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addProductAddClicked(ActionEvent actionEvent) throws IOException {
        Part sPart = addProdTable.getSelectionModel().getSelectedItem();
        // Verify a part is selected; add to the "Remove Part" Table View
        if (sPart == null) {
            alert(1);
        }
        else {
            aPart.add(sPart);
            removeProdTable.setItems(aPart);
            addProdTable.refresh();
            removeProdTable.refresh();
        }
    }

    /**
     * Removes a selected part from the "final" table before saving to product
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addProdRemoveClicked(ActionEvent actionEvent) throws IOException {
        Part sPart = removeProdTable.getSelectionModel().getSelectedItem();
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
    public void addProdSaveClicked(ActionEvent actionEvent) throws IOException {
        // Set Product data
        String name = addProdName.getText();
        double price = Double.parseDouble(addProdPrice.getText());
        int stock = Integer.parseInt(addProdInv.getText());
        int min = Integer.parseInt(addProdMin.getText());
        int max = Integer.parseInt(addProdMax.getText());
        // Validate min, max, inv
        if (min > max) {
            alert(5);
        }
        else if (stock < min || stock > max) {
            alert(6);
        }
        else {
            // Call Product constructor and add to inventory
            Product newProd = new Product(Inventory.createId(), name, price, stock, min, max);
            for (Part part : aPart) {
                newProd.addAssociatedPart(part);
            }
            Inventory.addProduct(newProd);
            // Return to main screen
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) addProdCancel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Cancel Button: Prompts user to confirm cancellation of adding new product - Returns to Main Menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addProdCancelClicked(ActionEvent actionEvent) throws IOException {
        alert(4);
    }

     // Alerts and Confirmations

    /**
     * Alert method used to warn users of errors.
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
                Part sPart = removeProdTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setContentText("Remove the " + sPart.getName() + " part from the product?");
                Optional<ButtonType> o = alert.showAndWait();
                if (o.isPresent() && o.get() == ButtonType.OK) {
                    aPart.remove(sPart);
                    removeProdTable.setItems(aPart);
                }
            }
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Change of mind?");
                alert.setContentText("Do you want to cancel changes and return to the main menu?");
                Optional<ButtonType> o = alert.showAndWait();
                if (o.isPresent() && o.get() == ButtonType.OK) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) addProdCancel.getScene().getWindow();
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
