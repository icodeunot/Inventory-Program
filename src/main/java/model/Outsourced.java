package model;
/**
 * This is the Outsourced class. It is used to work with Outsourced Projects.
 * @author John Salazar
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Outsourced Part constructor. Assigns Company Name.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Assigns Company Name to Outsourced Part.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns Outsourced Part's Company Name.
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}
