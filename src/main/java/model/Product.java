package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * This is the Product class. It is used to work with Product Objects.
 * @author John Salazar
 */
public class Product {
    /**
     * List of associated parts.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private int stock;
    private int min;
    private int max;
    private double price;
    private String name;

    /**
     * Product constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
        this.name = name;
    }

    /**
     * Sets id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Se's Inventory level
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets minimum
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets maximum
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Sets price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns Inventory level
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Returns Minimum
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns Maximum
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Returns price
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns Name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Add part to a list of associated parts.
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a selected part from associated list.
     * @param selectedAssociatedPart
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return false;
    }

    /**
     * Returns a list of associated parts.
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
