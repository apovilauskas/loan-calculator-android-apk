package org.example.interestcalculator;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class PaymentCalculator {
    protected double amt;
    protected int term;
    protected double intrst;

    protected int totalAmount;
    protected int totalInterest;
    protected LocalDate start;
    protected LocalDate fstart;
    protected LocalDate fend;
    protected double deferralInterest;
    protected int deferralMonths;
    protected LocalDate deferralStart;

    public PaymentCalculator(CalcParams calcParams){
        this.amt = calcParams.getAmt();
        this.term = calcParams.getTerm();
        this.intrst = calcParams.getIntrst();
        this.totalInterest =0;
        this.totalAmount =0;
        this.start = calcParams.getStart();
        this.fend = calcParams.getFend();
        this.fstart = calcParams.getFstart();
        this.deferralInterest = calcParams.getDeferralInterest();
        this.deferralMonths = calcParams.getDeferralMonths();
        this.deferralStart = calcParams.getDeferralStart();
    }

    protected boolean isDeferralMonth(int periodIndex) {
        if (deferralStart == null || deferralMonths <= 0) {
            return false;
        }
        int deferralIndex = (deferralStart.getYear() - start.getYear()) * 12
                + deferralStart.getMonthValue() - start.getMonthValue();
        return periodIndex >= deferralIndex && periodIndex < deferralIndex + deferralMonths;
    }

    public abstract Result calculate();
}
