package MyLib.Classes.Services;

import MyLib.Classes.Models.User;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author ymnis
 */
public class AuthService {
    private static ArrayList<User> userList = new ArrayList<>(); // Create the array
    private static User currentUser = null;
    
    static { // Pre-made data for testing;
        userList.add(new User("admin", "admin123", "admin@realestate.com", "Admin"));
        userList.add(new User("buyer", "buyer123", "buyer@gmail.com", "Buyer"));
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AuthService.currentUser = currentUser;
    }
    
    public static boolean emailExists(String email) {
        for (User u : userList) {
            if (u.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    
    public static void addUser(User user){
        userList.add(user);
        
        System.out.println("Account Created!");
    }
    
    public static void deleteUser(User user) {
        if (user == null) {
            System.out.println("Delete failed: No user logged in.");
            return;
        }

        userList.removeIf(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));
        currentUser = null;
        System.out.println("Account Deleted");
    }
    
    public static User authenticateUser(String email, String password) {
        for (User u : userList) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                setCurrentUser(u);
                return u;
            }
        }
        return null;
    }
}