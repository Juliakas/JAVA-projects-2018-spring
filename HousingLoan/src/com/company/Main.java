package com.company;
import IO.Gui;
import housing_loan.Calculator;
import user.Annuity;
import user.Linear;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Gui gui = new Gui();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(300, 200);
        gui.setVisible(true);

        while(!gui.isButtonPressed())
            Thread.sleep(200);
        Calculator calculator;
        gui.setUpTextArea();
        if (gui.getTypeOfLoan() == 'l')
            calculator = new Linear(gui.getTotalAmount(), gui.getTotalTerm(), gui.getYearlyInterest());
        else if (gui.getTypeOfLoan() == 'a')
            calculator = new Annuity(gui.getTotalAmount(), gui.getTotalTerm(), gui.getYearlyInterest());
        else throw new NullPointerException();
        for (int i = 1; i <= gui.getTotalTerm(); i++) {
            calculator.currentMonth();
            String text;
            text = Integer.toString(i);
            text += " month\tPayment: ";
            text += Double.toString(calculator.getThisMonthPayment());
            text += "\t\tInterest: " + Double.toString(calculator.getThisMonthInterest());
            text += "\t\tTotal: " + Double.toString(calculator.getThisMonthTotal())+ "\n";
            gui.addToTextArea(text);
        }
    }
}