package MyLib.Classes.Services;

import MyLib.Classes.Models.Duplex;
import MyLib.Classes.Models.Property;
import MyLib.Classes.Models.SingleAttached;
import MyLib.Classes.Models.Townhouse;
import java.util.HashMap;

public class PropertyService {
    private static HashMap<String, Property> inventory = new HashMap<>();
    
    static {
        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++) {
                String id = "B" + block + "-L" + lot;
                inventory.put(id, new Property(id, "Available"));
            }
        }
    }
    
    public static Property getProperty(int block, int lot) {
        return inventory.get("B" + block + "-L" + lot);
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
