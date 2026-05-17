package org.example.interestcalculator;

public class Payment {

    private String date;
    private double monthlyPayment;
    private double principalPart;
    private double interestPart;
    private double left;

    public Payment(String date, double monthlyPayment, double principalPart, double interestPart, double left) {
        this.date = date;
        this.monthlyPayment = Math.round(monthlyPayment*100.0)/100.0;
        this.principalPart = Math.round(principalPart*100.0)/100.0;
        this.interestPart = Math.round(interestPart*100.0)/100.0;
        this.left = Math.round(left*100.0)/100.0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getPrincipalPart() {
        return principalPart;
    }

    public void setPrincipalPart(double principalPart) {
        this.principalPart = principalPart;
    }

    public double getInterestPart() {
        return interestPart;
    }

    public void setInterestPart(double interestPart) {
        this.interestPart = interestPart;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }
}
