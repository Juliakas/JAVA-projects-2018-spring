package housing_loan;

public abstract class Calculator {

    protected double totalAmount;
    protected int totalTerm;
    protected double yearlyInterest;
    protected double thisMonthInterest;
    protected double thisMonthPayment;
    protected double remainingAmount;
    protected int remainingTerm;

    public Calculator(double totalAmount, int totalTerm, double yearlyInterest) {
        this.totalAmount = totalAmount;
        this.totalTerm = totalTerm;
        this.yearlyInterest = yearlyInterest * 0.01;
        remainingAmount = totalAmount;
        remainingTerm = totalTerm;
    }

    protected double monthlyPayment() {
        return totalAmount / totalTerm;
    }

    public abstract void currentMonth();

    public double getThisMonthTotal() {
        return Math.round((thisMonthPayment + thisMonthInterest) * 100) / 100.0;
    }

    public double getThisMonthPayment() {
        return Math.round(thisMonthPayment * 100) / 100.0;
    }

    public double getThisMonthInterest() {
        return Math.round(thisMonthInterest * 100) / 100.0;
    }

}
