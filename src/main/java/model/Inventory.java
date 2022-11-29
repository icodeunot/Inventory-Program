package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

    /**
     * This is the Inventory Class. It is used to maintain Parts and Products.
     * @author John Salazar
     */
public class Inventory {
    // Static variable and method for generating unique ID
    private static int idNum = 17;

        /**
         * Creates and returns a unique ID.
         * @return idNum
         */
    public static int createId() {
        idNum++;
        return idNum;
    }

        /**
         * A list of all parts.
         */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

        /**
         * A list of all Products.
         */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

        /**
         * Adds new part to list.
         * @param newPart
         */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

        /**
         * Adds new product to list.
         * @param newProduct
         */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

        /**
         * Uses ID to return related part.
         * @param partId
         * @return part
         */
    public static Part lookupPart(int partId) {
        for (Part part:allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

        /**
         * Uses ID to return related product.
         * @param productId
         * @return product
         */
    public static Product lookupProduct(int productId) {
        for (Product product:allProducts)  {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

        /**
         * Uses Name to return related part.
         * @param partName
         * @return partsContaining
         */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsContaining = FXCollections.observableArrayList();
        for (Part ptName:allParts) {
            if (ptName.getName().contains(partName)) {
                partsContaining.add(ptName);
            }
        }
        return partsContaining;
    }

        /**
         * Uses Name to return related product.
         * @param productName
         * @return productsContaining
         */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsContaining = FXCollections.observableArrayList();
        for (Product pdName:allProducts) {
            if (pdName.getName().contains(productName)) {
                productsContaining.add(pdName);
            }
        }
        return productsContaining;
    }

        /**
         * Used to change Part Object details
         * @param index
         * @param selectedPart
         */
    public static void updatePart(int index, Part selectedPart) {
        for (Part part:allParts) {
            if (part.getId() == selectedPart.getId()) {
                allParts.set(allParts.indexOf(part), selectedPart);
            }
        }
    }

        /**
         * Used to change Product Object details
         * @param index
         * @param newProduct
         */
    public static void updateProduct(int index, Product newProduct) {
        for (Product product:allProducts) {
            if (product.getId() == newProduct.getId()) {
                allProducts.set(allProducts.indexOf(product), newProduct);
            }
        }
    }

        /**
         * Used to delete a part.
         * @param selectedPart
         * @return deletePart boolean
         */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart != null) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

        /**
         * Used to delete a product.
         * @param selectedProduct
         * @return deleteProduct boolean
         */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

        /**
         * Returns all parts. Usually used to find parts for a product.
         * @return allParts list
         */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

        /**
         * Returns all Products. Usually used for queries.
         * @return allProducts list
         */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

        /**
         * Data used for testing. Called from the MainMenuController on first initialization of the main screen.
         */
    public static void addThisData() {

        Part wheel = new InHouse(1, "wheel", 20.0, 4, 1, 10, 3);
        Part tire = new InHouse(2, "tire", 200.00, 4, 1, 10, 4);
        Part mirror = new InHouse(3, "mirror", 2000.0, 4, 1, 10, 5);
        Part pedal = new InHouse(4, "pedal", 20000.0, 4, 1, 10, 6);
        Part shifter = new InHouse(5, "shifter", 200000.0, 4, 1, 10, 7);
        Inventory.addPart(wheel);
        Inventory.addPart(tire);
        Inventory.addPart(mirror);
        Inventory.addPart(pedal);
        Inventory.addPart(shifter);

        Product car = new Product(1, "car", 20.0, 4, 1, 10);
        Product bike = new Product(2, "bike", 200.00, 4, 1, 10);
        Product truck = new Product(3, "truck", 2000.0, 4, 1, 10);
        Product skateboard = new Product(4, "skateboard", 20000.0, 4, 1, 10);
        Product motorcycle = new Product(5, "motorcycle", 200000.0, 4, 1, 10);
        Inventory.addProduct(car);
        Inventory.addProduct(bike);
        Inventory.addProduct(truck);
        Inventory.addProduct(skateboard);
        Inventory.addProduct(motorcycle);
    }
}
