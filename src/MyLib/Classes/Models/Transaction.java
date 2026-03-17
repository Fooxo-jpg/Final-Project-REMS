package MyLib.Classes.Models;

import java.util.Date;

public class Transaction {
    private String transactionID;
    private Property property;
    private String paymentMethod;
    private double initialPayment;
    private Payment paymentDetail;
    private Date date;
    
    private String loanTerm;
    private double monthlyAmortization;
    
    public Transaction(Property property, String paymentMethod, double initialPayment,
            Payment paymentDetail, String loanTerm, double monthlyAmortization) {
        this.transactionID = "TRX-" + (1000 + new java.util.Random().nextInt(9000));
        this.property = property;
        this.paymentMethod = paymentMethod;
        this.initialPayment = initialPayment;
        this.paymentDetail = paymentDetail;
        this.loanTerm = loanTerm;
        this.monthlyAmortization = monthlyAmortization;
        this.date = new java.util.Date();
    }

    public String getTransactionID() { return transactionID; }
    public Property getProperty() { return property; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getInitialPayment() { return initialPayment; }
    public Date getDate() { return date; }
    public Payment getPaymentDetail() { return paymentDetail; }
    public String getLoanTerm() { return loanTerm; }
    public double getMonthlyAmortization() { return monthlyAmortization; }
    
}