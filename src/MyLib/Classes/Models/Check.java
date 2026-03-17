package MyLib.Classes.Models;

import java.util.Date;

public class Check extends Payment {

    private int checkNo;
    private int accNo;
    private String bankName;
    private Date checkDate;

    // CONSTRUCTOR
    public Check() {
        super();
        this.checkDate = new Date();
    }

    @Override
    public void processPayment() {
        System.out.println("Processing Check Payment...");
        System.out.println("Bank: " + bankName);
        System.out.println("Check Number: " + checkNo);
        System.out.println("Status: Pending Clearing");
    }

    // GETTERS AND SETTERS
    public int getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(int checkNo) {
        this.checkNo = checkNo;
    }

    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
