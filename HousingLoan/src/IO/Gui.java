package IO;
import housing_loan.Calculator;
import user.Annuity;
import user.Linear;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JFrame{

    private JButton button;
    private JButton fileButton;
    private JRadioButton linearButton;
    private JRadioButton annuityButton;
    private JTextField loanField;
    private JTextField termField;
    private JTextField interestField;
    private JTextField firstMonthField;
    private JTextField lastMonthField;
    private JTextArea textArea;
    private ButtonGroup group;

    private double totalAmount;
    private int totalTerm;
    private char typeOfLoan;
    private double yearlyInterest;
    private boolean buttonIsPressed = false;

    public Gui() {
        super("Housing loan calculator");
        setLayout(new FlowLayout());
        loanField = new PTextField("Loan", 10);
        add(loanField);
        termField = new PTextField("Term (in months)", 15);
        add(termField);
        interestField = new PTextField("Yearly interest", 15);
        add(interestField);
        linearButton = new JRadioButton("Linear");
        add(linearButton);
        annuityButton = new JRadioButton("Annuity");
        add(annuityButton);
        group = new ButtonGroup();
        group.add(linearButton);
        group.add(annuityButton);

        button = new JButton("Calculate");
        add(button);

        fileButton = new JButton("File report");
        add(fileButton);
        firstMonthField = new PTextField("First month", 11);
        add(firstMonthField);
        lastMonthField = new PTextField("Last month", 11);
        add(lastMonthField);

        HandlerClass handler = new HandlerClass();
        button.addActionListener(handler);
        fileButton.addActionListener(handler);
    }

    public Gui(boolean temp) {

    }

    public void setUpTextArea() {
        textArea = new JTextArea(25, 50);
        textArea.setEditable(false);
        JPanel panel = new JPanel ();
        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add (scroll);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addToTextArea(String text) {
        String currentText = textArea.getText();
        currentText += text;
        textArea.setText(currentText);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getTotalTerm() {
        return totalTerm;
    }

    public char getTypeOfLoan() {
        return typeOfLoan;
    }

    public double getYearlyInterest() {
        return yearlyInterest;
    }

    public boolean isButtonPressed() {
        return buttonIsPressed;
    }

    private class HandlerClass implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            totalAmount = Double.parseDouble(loanField.getText());
            totalTerm = Integer.parseInt(termField.getText());
            yearlyInterest = Double.parseDouble(interestField.getText());
            if (linearButton.isSelected()) typeOfLoan = 'l';
            else if (annuityButton.isSelected()) typeOfLoan = 'a';
            if(event.getSource().equals(button)) {
                buttonIsPressed = true;
            }
            else if(event.getSource().equals(fileButton)) {
                OutputFile file = new OutputFile();
                Calculator calculator;
                if(typeOfLoan == 'l')  calculator = new Linear(totalAmount, totalTerm, yearlyInterest);
                else if(typeOfLoan == 'a') calculator = new Annuity(totalAmount, totalTerm, yearlyInterest);
                else throw new NullPointerException();
                for (int i = 1; i <= getTotalTerm(); i++) {
                    calculator.currentMonth();
                    String text;
                    if(i >= Integer.parseInt(firstMonthField.getText()) && i <= Integer.parseInt(lastMonthField.getText())) {
                        text = Integer.toString(i);
                        text += " month\tPayment: ";
                        text += Double.toString(calculator.getThisMonthPayment());
                        text += "\t\tInterest: " + Double.toString(calculator.getThisMonthInterest());
                        text += "\t\tTotal: " + Double.toString(calculator.getThisMonthTotal()) + "\n";
                        file.writeln(text);
                    }
                }
                file.closeFile();
            }
        }
    }
}
