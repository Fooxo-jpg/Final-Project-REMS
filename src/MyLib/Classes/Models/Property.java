package MyLib.Classes.Models;

public class Property {
    protected String propertyID;
    protected String address;
    protected String status;
    protected double lotArea;
    protected double floorArea;
    protected double netSellingPrice;
    protected int numBedrooms;
    protected int numBathrooms;
    
    // CONSTRUCTORS
    public Property(String propertyID, String status) {
        this.propertyID = propertyID;
        this.status = status;
    }
    
    
    // GETTERS
    public String getPropertyID() { return propertyID; }
    public String getAddress() { return address; }
    public String getStatus() { return status; }
    public double getLotArea() { return lotArea; }
    public double getFloorArea() { return floorArea; }
    public double getNetSellingPrice() { return netSellingPrice; }
    public int getNumBedrooms() { return numBedrooms; }
    public int getNumBathrooms() { return numBathrooms; }
    
    // SETTERS
    public void setPropertyID(String propertyID) { this.propertyID = propertyID; }
    public void setAddress(String address) { this.address = address; }
    public void setStatus(String status) { this.status = status; }
    public void setLotArea(double lotArea) { this.lotArea = lotArea; }
    public void setFloorArea(double floorArea) { this.floorArea = floorArea; }
    public void setNetSellingPrice(double netSellingPrice) { this.netSellingPrice = netSellingPrice; }
    public void setNumBedrooms(int numBedrooms) { this.numBedrooms = numBedrooms; }
    public void setNumBathrooms(int numBathrooms) { this.numBathrooms = numBathrooms; }
    
    // METHODS
    public int calculatePricePerSqFt(int lotArea) {
        return lotArea * 4000;
    }
    
    public void updateStatus(){
        
    }
    
    public void reserveProperty(){
        
    }
}