package org.example.interestcalculator;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AnnuityCalculator extends PaymentCalculator {

    public AnnuityCalculator(CalcParams calcParams) {
        super(calcParams);
    }

    public Result calculate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");

        if (fstart.isBefore(start)) fstart = start;
        if (fend.isAfter(start.plusMonths(term + deferralMonths))) fend = start.plusMonths(term + deferralMonths);

        ArrayList<Payment> payments = new ArrayList<>(term + deferralMonths);
        double remainingBalance = amt;
        double totalInterest = 0;
        double totalPaid = 0;
        int principalPaymentsMade = 0;

        for (int i = 0; i < term + deferralMonths; i++) {
            double interestPortion;
            double principalPortion = 0;

            if (isDeferralMonth(i)) {
                interestPortion = remainingBalance * deferralInterest;
            } else {
                int remainingPrincipalPayments = term - principalPaymentsMade;
                interestPortion = remainingBalance * intrst;
                double monthlyPayment = annuityPayment(remainingBalance, intrst, remainingPrincipalPayments);
                principalPortion = remainingPrincipalPayments == 1 ? remainingBalance : monthlyPayment - interestPortion;
                remainingBalance -= principalPortion;
                principalPaymentsMade++;
            }

            totalPaid += principalPortion + interestPortion;
            totalInterest += interestPortion;

            if (!fstart.isAfter(start.plusMonths(i)) && !fend.isBefore(start.plusMonths(i))) {
                String date = start.plusMonths(i).format(formatter);
                payments.add(new Payment(date, principalPortion + interestPortion, principalPortion, interestPortion, remainingBalance));
            }
        }

        totalPaid = Math.round(totalPaid * 100.0) / 100.0;
        totalInterest = Math.round(totalInterest * 100.0) / 100.0;
        String resultMessage = "Total amount: " + totalPaid + " EUR, of which interest is: " + totalInterest + " EUR";
        return new Result(payments, resultMessage);
    }

    private double annuityPayment(double principal, double monthlyRate, int months) {
        if (months <= 0) {
            return 0;
        }
        if (monthlyRate == 0) {
            return principal / months;
        }
        double compoundFactor = Math.pow(1 + monthlyRate, months);
        return principal * (monthlyRate * compoundFactor) / (compoundFactor - 1);
    }
}
