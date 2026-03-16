package MyLib.Classes.Models;

public class Cash extends Payment {

    private String receivedBy;
    private double discountRate = 0.10;
    private java.util.Date fullPaymentDate;

    @Override
    public void processPayment() {

    }
}
