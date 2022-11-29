package model;
/**
 * This is the InHouse class. It is used to work with In-House Part objects.
 * @author John Salazar
 */
public class InHouse extends Part {

    private int machineId;
    /**
     * InHouse Part constructor. Assigns Machine ID.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * Assigns machineId to In-House Part
     * @param machineId assigns machineId to In-House Part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /**
     * Return the Part's Machine ID
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
