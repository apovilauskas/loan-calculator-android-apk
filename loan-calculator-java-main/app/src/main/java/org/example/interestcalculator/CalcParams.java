package org.example.interestcalculator;

import java.time.LocalDate;

public class CalcParams {
    private double amt;
    private int deferralMonths, term;
    private double intrst, deferralInterest;
    private LocalDate start, fstart, fend, deferralStart;

    public CalcParams(double amt, int deferralMonths, int term, double intrst, double deferralInterest, LocalDate start, LocalDate fstart, LocalDate fend, LocalDate deferralStart) {
        this.amt = amt;
        this.deferralMonths = deferralMonths;
        this.term = term;
        this.intrst = intrst;
        this.deferralInterest = deferralInterest;
        this.start = start;
        this.fstart = fstart;
        this.fend = fend;
        this.deferralStart = deferralStart;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public int getDeferralMonths() {
        return deferralMonths;
    }

    public void setDeferralMonths(int deferralMonths) {
        this.deferralMonths = deferralMonths;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getIntrst() {
        return intrst;
    }

    public void setIntrst(double intrst) {
        this.intrst = intrst;
    }

    public double getDeferralInterest() {
        return deferralInterest;
    }

    public void setDeferralInterest(double deferralInterest) {
        this.deferralInterest = deferralInterest;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFstart() {
        return fstart;
    }

    public void setFstart(LocalDate fstart) {
        this.fstart = fstart;
    }

    public LocalDate getFend() {
        return fend;
    }

    public void setFend(LocalDate fend) {
        this.fend = fend;
    }

    public LocalDate getDeferralStart() {
        return deferralStart;
    }

    public void setDeferralStart(LocalDate deferralStart) {
        this.deferralStart = deferralStart;
    }
}
