package user;

import housing_loan.Calculator;

public class Annuity extends Calculator {

    public Annuity(double totalAmount, int totalTerm, double yearlyInterest) {
        super(totalAmount, totalTerm, yearlyInterest);
    }

    private double thisMonthTotal() {
        return totalAmount * creditSize();
    }

    private double creditSize() {
        return (monthlyInterest() * Math.pow(1 + monthlyInterest(), totalTerm)) / (Math.pow(1 + monthlyInterest(), totalTerm) - 1);
    }

    private double monthlyInterest() {
        return yearlyInterest / 12;
    }

    public void currentMonth() {
        thisMonthInterest = remainingAmount * (yearlyInterest / 12);
        thisMonthPayment = thisMonthTotal() - thisMonthInterest;
        remainingAmount = remainingAmount - monthlyPayment();
        remainingTerm--;
    }
}
