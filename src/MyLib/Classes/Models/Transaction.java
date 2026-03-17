package MyLib.Classes.Models;

import java.util.Date;

public class Transaction {
    private String transactionID;
    private String buyerUsername;
    private Property property;
    private String paymentMethod;
    private double initialPayment;
    private Payment paymentDetail;
    private Date date;
    
    private String loanTerm;
    private double monthlyAmortization;
    
    private double annualIncome;
    private String status;
    
    public Transaction(String buyerUsername, Property property, String paymentMethod,
            double initialPayment, Payment paymentDetail,
            String loanTerm, double monthlyAmortization, double annualIncome) {
        this.transactionID = "TRX-" + (1000 + new java.util.Random().nextInt(9000));
        this.buyerUsername = buyerUsername;
        this.property = property;
        this.paymentMethod = paymentMethod;
        this.initialPayment = initialPayment;
        this.paymentDetail = paymentDetail;
        this.loanTerm = loanTerm;
        this.monthlyAmortization = monthlyAmortization;
        this.annualIncome = annualIncome;
        this.date = new java.util.Date();

        // FIX: Dynamic Status Assignment
        if (paymentMethod.equalsIgnoreCase("In-House Financing")) {
            this.status = "Pending Inhouse Loan";
        } else {
            this.status = "Finalized";
        }
    }

    public String getTransactionID() { return transactionID; }
    public Property getProperty() { return property; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getInitialPayment() { return initialPayment; }
    public Date getDate() { return date; }
    public Payment getPaymentDetail() { return paymentDetail; }
    public String getLoanTerm() { return loanTerm; }
    public double getMonthlyAmortization() { return monthlyAmortization; }
    public String getBuyerUsername() { return buyerUsername; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getAnnualIncome() { return annualIncome; }
    
}