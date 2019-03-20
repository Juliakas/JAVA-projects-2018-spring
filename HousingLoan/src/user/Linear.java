package user;

import housing_loan.Calculator;

public class Linear extends Calculator {

    public Linear(double totalAmount, int totalTerm, double yearlyInterest) {
        super(totalAmount, totalTerm, yearlyInterest);
    }

    public void currentMonth(){
        thisMonthInterest = remainingAmount * (yearlyInterest / 12);
        thisMonthPayment = monthlyPayment();
        remainingAmount = remainingAmount - monthlyPayment();
        remainingTerm--;
    }
}
