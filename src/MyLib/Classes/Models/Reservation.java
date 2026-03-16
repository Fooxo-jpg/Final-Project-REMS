package MyLib.Classes.Models;

public class Reservation extends Payment {

    private double reservationFee = 40000.00;

    @Override
    public void processPayment() {
        System.out.println("Processing Reservation Ref: " + referenceNumber);
        // Logic to set property status to "Reserved" goes here
    }
}