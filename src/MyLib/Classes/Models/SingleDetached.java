package MyLib.Classes.Models;

public class SingleDetached extends Property {
    private double bankDistance;
    private double gardenArea;

    public SingleDetached(String propertyID, String status) {
        super(propertyID, status);
        this.houseType = "Single Detached";
    }

    // GETTERS
    public double getBankDistance() { return bankDistance; }
    public double getGardenArea() { return gardenArea; }

    // SETTERS 
    public void setBankDistance(double bankDistance) { this.bankDistance = bankDistance; }
    public void setGardenArea(double gardenArea) { this.gardenArea = gardenArea; }
}
