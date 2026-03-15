package MyLib.Classes.Models;

public class Townhouse extends Property{
    private String unitPosition;
    private int numFloors;

    public Townhouse(String propertyID, String status) {
        super(propertyID, status);
        this.houseType = "Townhouse";
    }

    public String getUnitPosition() { return unitPosition; }
    public int getNumFloors() { return numFloors; }

    public void setUnitPosition(String unitPosition) { this.unitPosition = unitPosition; }
    public void setNumFloors(int numFloors) { this.numFloors = numFloors; }
}
