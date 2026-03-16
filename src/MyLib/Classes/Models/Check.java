package MyLib.Classes.Models;

public class Check extends Payment {

    private int checkNo;
    private int accNo;
    private String bankName;
    private java.util.Date checkDate;

    @Override
    public void processPayment() {
        // Logic for Check clearing
    }
}
