package MyLib.Classes.Models;

public class Bank extends Loan {

    private String bankName;
    private boolean DTI;
    private java.util.Date approvalDate;

    public Bank(String bankName, double principal, int years) {
        this.bankName = bankName;
        this.principalAmount = principal;
        this.interestRate = 0.085;
        this.termMonths = years * 12;
    }

    @Override
    public void processPayment() {
        System.out.println("Bank Loan via " + bankName + " processed.");
    }
}
