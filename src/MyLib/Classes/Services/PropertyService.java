package MyLib.Classes.Services;

import MyLib.Classes.Models.*;
import java.util.*;

public class PropertyService {
    private static HashMap<String, Property> inventory = new HashMap<>();
    private static final Random random = new Random(1);
    
    static {
        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++) {
                Property p = generateEstates(block, lot);
                inventory.put(p.getPropertyID(), p);
            }
        }
    }
    
    public static Property getProperty(int block, int lot) {
        return inventory.get("B" + block + "-L" + lot);
    }
    
    private static Property generateEstates(int block, int lot) {
        String id = "B" + block + "-L" + lot;
        Property p;

        switch (lot) {
            case 1, 2, 3, 19, 20 -> {
                p = new SingleDetached(id, "Available");
                p.setLotArea(150 + (50 * random.nextDouble()));
                p.setNumBedrooms(random.nextInt(3) + 4); // 4 to 6
                p.setNumBathrooms(random.nextInt(3) + 3); // 3 to 5
            }
            
            case 6, 8, 12, 13, 14, 15 -> {
                p = new SingleAttached(id, "Available");
                p.setLotArea(110 + (40 * random.nextDouble())); // 110 to 150
                p.setNumBedrooms(random.nextInt(3) + 2); // 2 to 4
                p.setNumBathrooms(random.nextInt(2) + 2); // 2 to 3
            }
            
            default -> {
                p = new Townhouse(id, "Available");
                p.setLotArea(80 + (30 * random.nextDouble()));// 80 to 110
                p.setNumBedrooms(random.nextInt(2) + 1); //1 to 2
                p.setNumBathrooms(1);
            }
        }
        
        p.setLotArea(Math.round(p.getLotArea() * 100.0) / 100.0);
        
        double multiplier = 0.6 + (0.3 * random.nextDouble());
        double floorArea = p.getLotArea() * multiplier;
        
        p.setFloorArea(Math.round(floorArea * 100.0) / 100.0);
        p.setListed(false);
        
        return p;
    }
    
    public static int getCountByStatus(String status) {
        int count = 0;
        for (Property p : inventory.values()) {
            if (p.getStatus().equalsIgnoreCase(status)) {
                count++;
            }
        }
        return count;
    }

    public static int getTotalProperties() {
        return inventory.size();
    }
    
    public static void updateProperty(Property updatedProp) {
        inventory.put(updatedProp.getPropertyID(), updatedProp);
        System.out.println("Property " + updatedProp.getPropertyID() + " updated to " + updatedProp.getClass().getSimpleName());
    }
}
