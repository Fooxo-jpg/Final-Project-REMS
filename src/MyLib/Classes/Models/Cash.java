package MyLib.Classes.Models;

import java.util.Date;

public class Cash extends Payment {

    private String receivedBy;
    private double discountRate;
    private Date fullPaymentDate;

    // CONSTRUCTOR
    public Cash() {
        super();
        this.discountRate = 0.10;
        this.fullPaymentDate = new Date();
    }
    
    @Override
    public void processPayment() {
        System.out.println("Processing Cash Payment...");
        System.out.println("Reference No: " + getReferenceNumber());
        System.out.println("Received by Agent: " + receivedBy);
    }

    // GETTERS AND SETTERS
    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public Date getFullPaymentDate() {
        return fullPaymentDate;
    }

    public void setFullPaymentDate(Date fullPaymentDate) {
        this.fullPaymentDate = fullPaymentDate;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
