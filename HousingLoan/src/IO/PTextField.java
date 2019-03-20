package IO;
//import java.swing.JTextField

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PTextField extends JTextField {

    public PTextField(final String proptText, int size) {
        super(proptText, size);
        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(proptText);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(proptText)) {
                    setText("");
                }
            }
        });

    }
}