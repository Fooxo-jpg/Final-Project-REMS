package MyLib.Classes.Models;

import java.util.Date;

public class Transaction {
    private String transactionID;
    private Property property;
    private String paymentMethod;
    private double initialPayment;
    private Date date;
    
    public Transaction(Property property, String paymentMethod, double initialPayment) {
        this.transactionID = "TRX-" + (1000 + new java.util.Random().nextInt(9000));
        this.property = property;
        this.paymentMethod = paymentMethod;
        this.initialPayment = initialPayment;
        this.date = new Date();
    }

    public String getTransactionID() { return transactionID; }
    public Property getProperty() { return property; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getInitialPayment() { return initialPayment; }
    public Date getDate() { return date; }
    
}