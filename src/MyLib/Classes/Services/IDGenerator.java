package MyLib.Classes.Services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDGenerator {

    private static final Random random = new Random();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * Generates a Transaction ID: TRX-YYYYMMDD-XXXX (e.g., TRX-20260318-4921)
     */
    public static String generateTransactionID() {
        String datePart = dateFormat.format(new Date());
        int randomPart = 1000 + random.nextInt(9000); // 4-digit random number
        return "TRX-" + datePart + "-" + randomPart;
    }

    /**
     * Generates a Payment Reference Number: REF-XXXXXXX (e.g., REF-8291043)
     */
    public static int generateReferenceNumber() {
        return 1000000 + random.nextInt(9000000);
    }

    /**
     * Generates a unique Check Number (6-digit integer standard)
     */
    public static int generateCheckNumber() {
        return 100000 + random.nextInt(900000);
    }
}
