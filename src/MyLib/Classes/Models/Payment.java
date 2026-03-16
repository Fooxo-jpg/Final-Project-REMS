package MyLib.Classes.Models;

import java.util.Date;

public abstract class Payment {
    protected int referenceNumber;
    protected Date paymentDate;
    protected double taxAmount; // VAT
    protected double lmfAmount; // Legal/Misc Fees

    public Payment() {
        this.referenceNumber = 100000 + (int) (Math.random() * 900000);
        this.paymentDate = new Date();
    }

    public abstract void processPayment();

    public int getReferenceNumber() {
        return referenceNumber;
    }
}