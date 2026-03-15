package MyLib.Classes.Models;

public class SingleAttached extends Property {
    private String sharedWallSide;
    private boolean hasFireWall;
    
    public SingleAttached(String propertyID, String status) {
        super(propertyID, status);
        this.houseType = "Single Attached";
    }

    // GETTERS
    public String getSharedWallSide() { return sharedWallSide; }
    public boolean HasFireWall() { return hasFireWall; }
    
    // SETTERS
    public void setSharedWallSide(String sharedWallSide) { this.sharedWallSide = sharedWallSide; }
    public void setFireWall(boolean hasFireWall) { this.hasFireWall = hasFireWall; }
    
}
