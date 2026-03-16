package MyLib.Classes.Services;

import MyLib.Classes.Models.Property;

public class FinancialCalculator {
    public static final double PROFIT_MARKUP = 0.15; // PROFIT NI ADMIN KAKALIST
    public static final double VAT_RATE = 0.12; // 12% IF ABOVE CERTAIN MILLIONS KASE KURAKOT GOBYERNO Taxes haha saket
    public static final double LMF_RATE = 0.10; // 10% LEGAL AND MISC FEES
    
    // BDO - Conservative/Standard
    public static final double BDO_INTEREST = 0.075; // 7.5%
    public static final double BDO_DP_PERCENT = 0.20; // Needs 20% DP
    
    // RCBC - Competitive/Promo
    public static final double RCBC_INTEREST = 0.068; // 6.8%
    public static final double RCBC_DP_PERCENT = 0.15; // Needs 15% DP
    
    // In-House - Flexible but Expensive
    public static final double INHOUSE_INTEREST = 0.12; // 12%
    public static final double INHOUSE_DP_PERCENT = 0.30; // Needs 30% DP
    public static final int INHOUSE_MAX_YEARS = 10; // Usually shorter terms
    
    public static final double RESERVATION_FEE = 40000.00;
    public static final double DP_PERCENT = 0.15; // 15% DOWNPAYMENT
    public static final int DP_TERMS = 18;
    public static final double BANK_INTEREST_RATE = 0.085; // 8.5%
    
    public static double calculateNSP(Property p) {
        double rawPrice = p.calculatePricePerSqFt(); // This is your base cost
        return rawPrice * (1 + PROFIT_MARKUP);
    }
    
    public static double calculateTCP(Property p) {
        double nsp = calculateNSP(p);
        double vat = nsp * VAT_RATE;
        double lmf = nsp * LMF_RATE;
        return nsp + vat + lmf;
    }
    
    public static double calculateTotalDP(double tcp) {
        return tcp * DP_PERCENT;
    }
    
    public static double calculateMonthlyDP(double totalDP) {
        return totalDP / DP_TERMS;
    }

    public static double calculateLoanableAmount(double tcp) {
        return tcp - calculateTotalDP(tcp);
    }

    public static double calculateMonthlyAmortization(double loanAmount, int years) {
        if (loanAmount <= 0) {
            return 0;
        }
        double monthlyInterest = BANK_INTEREST_RATE / 12;
        int numberOfPayments = years * 12;

        return (loanAmount * monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
    }
}