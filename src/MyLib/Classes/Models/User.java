package MyLib.Classes.Models;

public class User {
    protected String username;
    protected String password;
    protected String email;
    protected final String role;
    
    protected String firstName = "";
    protected String lastName = "";
    protected String contactNumber = "";
    protected String gender = "Select";
    

    // CONSTRUCTOR
    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // SETTERS
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setContactNumber(String contact) { this.contactNumber = contact; }
    public void setGender(String gender) { this.gender = gender; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

    // GETTERS
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getContactNumber() { return contactNumber; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}