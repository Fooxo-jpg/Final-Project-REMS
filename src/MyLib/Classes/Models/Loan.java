package MyLib.Classes.Models;

public abstract class Loan extends Payment {

    protected String loanID;
    protected double principalAmount;
    protected double interestRate;
    protected int termMonths;
    protected double monthlyAmortization;
    protected double loanablePercentage;

    @Override
    public abstract void processPayment();
}
