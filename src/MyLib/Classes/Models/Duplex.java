package MyLib.Classes.Models;

public class Duplex extends Property {

    private String unitSide; // "Left" or "Right"
    private double totalBuildingArea;

    public Duplex(String id, String status) {
        super(id, status);
        this.houseType = "Duplex";
    }

    public String getUnitSide() { return unitSide; }
    public double getTotalBuildingArea() { return totalBuildingArea; }

    public void setUnitSide(String unitSide) { this.unitSide = unitSide; }
    public void setTotalBuildingArea(double totalBuildingArea) { this.totalBuildingArea = totalBuildingArea; }
}
