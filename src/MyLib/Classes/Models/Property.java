package MyLib.Classes.Models;

public class Property {
    protected String propertyID;
    protected String houseType = "Generic";
    protected String address;
    protected String status;
    protected String assignedAgent = "No Agent Assigned";
    protected boolean isListed = false;
    protected double pricePerSQM = 4000.00;
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
    public String getAssignedAgent() { return assignedAgent; }
    public double getLotArea() { return lotArea; }
    public double getFloorArea() { return floorArea; }
    public double getNetSellingPrice() { return netSellingPrice; }
    public int getNumBedrooms() { return numBedrooms; }
    public int getNumBathrooms() { return numBathrooms; }
    public boolean isListed() { return isListed; }
    public double getPricePerSQM() { return pricePerSQM; }
    public String getHouseType() { return houseType; }
    
    // SETTERS
    public void setPropertyID(String propertyID) { this.propertyID = propertyID; }
    public void setAddress(String address) { this.address = address; }
    public void setStatus(String status) { this.status = status; }
    public void setAssignedAgent(String assignedAgent) { this.assignedAgent = assignedAgent; }
    public void setLotArea(double lotArea) { this.lotArea = lotArea; }
    public void setFloorArea(double floorArea) { this.floorArea = floorArea; }
    public void setNetSellingPrice(double netSellingPrice) { this.netSellingPrice = netSellingPrice; }
    public void setNumBedrooms(int numBedrooms) { this.numBedrooms = numBedrooms; }
    public void setNumBathrooms(int numBathrooms) { this.numBathrooms = numBathrooms; }
    public void setListed(boolean isListed) { this.isListed = isListed; }
    public void setPricePerSQM(double pricePerSQM) { this.pricePerSQM = pricePerSQM; }
    public void setHouseType(String houseType) { this.houseType = houseType; }
    
    // METHODS
    public double calculatePricePerSqFt() {
        return this.lotArea * this.pricePerSQM;
    }
    
    public void updateStatus(){
        
    }
    
    public void reserveProperty(){
        
    }
}