package MyLib.Classes.Models;

import java.util.ArrayList;

public class Buyer extends User{
    private double grossIncome;
    private ArrayList<Object> transactionHistory;

    // CONSTRUCTOR
    public Buyer(String username, String password, String email, double grossIncome) {
        super(username, password, email, "Buyer");
        this.grossIncome = grossIncome;
        this.transactionHistory = new ArrayList<>();
    }
    
    // SETTER
    public void setGrossIncome(double grossIncome) { this.grossIncome = grossIncome; }
    
    // GETTER
    public double getGrossIncome() { return grossIncome; }
    
    
    // METHODS
    public void searchProperty() {
        
    }
}
