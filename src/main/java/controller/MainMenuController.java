package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the MainMenuController. It is used to control the Main Menu GUI.
 * @author John Salazar
 */
public class MainMenuController implements Initializable {

    // Variables and Widgets

    private static int mainReturns = 1; // switch to 0 to populate data

    private static Part sPart;
    private static Product sProd;
    @FXML
    public Button mainPartsAddButton;
    @FXML
    public Button mainPartsModifyButton;
    @FXML
    public Button mainPartsDeleteButton;
    @FXML
    public Button mainPartsSearchButton;
    @FXML
    public Button mainProductsAddButton;
    @FXML
    public Button mainProductsModifyButton;
    @FXML
    public Button mainProductsDeleteButton;
    @FXML
    public Button mainProductsSearchButton;
    @FXML
    public Button mainMenuExitButton;

     // Parts Table

    @FXML
    private TextField mainPartsSearchField;
    @FXML
    private TableView<Part> mainPartsTable;
    @FXML
    private TableColumn<Part, Integer> idColMainPartsTable;
    @FXML
    private TableColumn<Part, String> nameColMainPartsTable;
    @FXML
    private TableColumn<Part, Integer> inventoryColMainPartsTable;
    @FXML
    private TableColumn<Part, Double> priceColMainPartsTable;

     // Products Table

    @FXML
    private TextField mainProductsSearchField;
    @FXML
    private TableView<Product> mainProductsTable;
    @FXML
    private TableColumn<Product, Integer> idColMainProductsTable;
    @FXML
    private TableColumn<Product, String> nameColMainProductsTable;
    @FXML
    private TableColumn<Product, Integer> inventoryColMainProductsTable;
    @FXML
    private TableColumn<Product, Double> priceColMainProductsTable;

     // MainMenuController Methods

    /**
     * Get Part data
     * @return sPart
     */
    public static Part getPart() {
        return sPart;
    }

    /**
     * Get Product Data
     * @return sProd
     */
    public static Product getProduct() {
        return sProd;
    }

    /**
     * Populate and Assign the "Parts" and "Products" table views and columns on initialization
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Determine Main Menu visits and populate Inventory for testing.
        if (mainReturns < 1) {
            System.out.println("I'm just getting started...");
            Inventory.addThisData();
        }
        else {
            if (mainReturns == 1) {
                System.out.println("You have returned to the main screen " + mainReturns + " time.");
            }
            else {
                System.out.println("You have returned to the main screen " + mainReturns + " times.");
            }
        }
        // Count times returned to main menu
        mainReturns++;
        // Assign inventory data to the table views
        mainPartsTable.setItems(Inventory.getAllParts());
        mainProductsTable.setItems(Inventory.getAllProducts());
          // Parts table view columns
        idColMainPartsTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColMainPartsTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColMainPartsTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColMainPartsTable.setCellValueFactory(new PropertyValueFactory<>("price"));
          // Products table view columns
        idColMainProductsTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColMainProductsTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColMainProductsTable.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColMainProductsTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Populate the tables with current Inventory on return to Main Menu
        mainPartsTable.refresh();
        mainProductsTable.refresh();
    }

     // Main Menu Event Handlers

    /**
     * Add Part Button: Navigates to "Add Part" form
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void mainPartsAddClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) mainPartsAddButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Modify Part Button: Navigates to "Modify Part" form when part selected from table - Alerts if no part selected
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void mainPartsModifyClick(ActionEvent actionEvent) throws IOException {
        sPart = mainPartsTable.getSelectionModel().getSelectedItem();
        if (sPart == null) {
            alert(1);
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete Part Button: Deletes selected part on confirmation alert - Alerts if no part selected
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void mainPartsDeleteClick(ActionEvent actionEvent) throws IOException {
        sPart = mainPartsTable.getSelectionModel().getSelectedItem();
        if (sPart == null) {
            alert(2);
        }
        else {
            alert(3);
        }
    }

    /**
     * Search Parts Button: Uses mainPartsSearchField text to Search the Parts inventory by ID or Name
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void mainPartsSearchClick(ActionEvent actionEvent) throws IOException {
        String userInput = mainPartsSearchField.getText();
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
        // Populate a Search Results table and re-set the Search Field
        mainPartsTable.setItems(results);
        mainPartsSearchField.setText("");
    }

    /**
     * Add Product Button: Navigates to "Add Products" form
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void mainProductsAddClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) mainProductsAddButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Modify Product Button: Navigates to "Modify Product" form when product selected from table - Alerts if no product selected
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void mainProductsModifyClick(ActionEvent actionEvent) throws IOException {
        sProd = mainProductsTable.getSelectionModel().getSelectedItem();
        if (sProd == null) {
            alert(5);
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete Product Button: Deletes selected product on confirmation alert - Alerts if no product selected
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void mainProductsDeleteClick(ActionEvent actionEvent) throws IOException {
        sProd = mainProductsTable.getSelectionModel().getSelectedItem();
        if (sProd == null) {
            alert(6);
        }
        else {
            alert(7);
        }
    }

    /**
     * Search Products Button: Uses mainProductsSearchField text to Search the Product inventory by ID or Name
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void mainProductsSearchClick(ActionEvent actionEvent) throws IOException {
        String userInput = mainProductsSearchField.getText();
        ObservableList<Product> results = Inventory.lookupProduct(userInput);
        // Check by Name, if no results check by ID
        if (results.size() == 0) {
            try {
                int id = Integer.parseInt(userInput);
                Product p = Inventory.lookupProduct(id);
                if (p != null) {
                    results.add(p);
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }
        // Populate a Search Results table and re-set the Search Field
        mainProductsTable.setItems(results);
        mainProductsSearchField.setText("");
    }

    /**
     * Prompt confirmation for exiting the program.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void mainMenuExitClick(ActionEvent actionEvent) throws IOException {
        alert(4);
    }

     // Alerts and Confirmations

    /**
     * Alert method used to warn user of errors.
     * @param alertNum
     */
    private void alert(int alertNum) {
        switch (alertNum) {
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("PLEASE SELECT A PART");
                alert.setContentText("To modify a part, please make a selection from the \"Parts\" table.");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("PLEASE SELECT A PART");
                alert.setContentText("To delete a part, please make a selection from the \"Parts\" table.");
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure you wanna do that?");
                alert.setContentText("Are you sure you want to delete \"" +
                                      mainPartsTable.getSelectionModel().getSelectedItem().getName() +
                                      "\"?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deletePart(mainPartsTable.getSelectionModel().getSelectedItem());
                    mainPartsTable.refresh();
                }
            }
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Leaving so soon?");
                alert.setContentText("Are you sure you want to exit the program?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("PLEASE SELECT A PRODUCT");
                alert.setContentText("To modify a product, please make a selection from the \"Products\" table.");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("PLEASE SELECT A PRODUCT");
                alert.setContentText("To delete a product, please make a selection from the \"Products\" table.");
                alert.showAndWait();
            }
            case 7 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure you wanna do that?");
                alert.setContentText("Are you sure you want to delete \"" +
                        mainProductsTable.getSelectionModel().getSelectedItem().getName() +
                        "\"?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ObservableList<Part> ap = sProd.getAllAssociatedParts();
                        if (ap.size() >= 1) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Can't do that yet.");
                            alert.setHeaderText("PRODUCT HAS ASSOCIATED PARTS");
                            alert.setContentText("Please use the \"Modify\" feature to delete the product's associated parts.");
                            alert.showAndWait();
                        }
                        else {
                            Inventory.deleteProduct(mainProductsTable.getSelectionModel().getSelectedItem());
                            mainProductsTable.refresh();
                        }
                }
            }
        }
    }
}