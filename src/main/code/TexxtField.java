package main.code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TexxtField extends JLabel {
    private static final long serialVersionUID = 1L;
    private String mess = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/Teehee.png"; // Absolute path for the image
    private JTextField textField;

    public TexxtField(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);      

        // Load and resize the image icon
        ImageIcon icon = new ImageIcon(mess);
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            this.setIcon(icon);
        }

        // Initialize the JTextField
        textField = new JTextField(20); // Set the initial columns

        // Set the size and position of the JTextField
        textField.setBounds(width / 2 - Utility.scaleW(60), height / 2 - Utility.scaleH(14), Utility.scaleW(120), Utility.scaleH(30)); // Adjust the size and position as needed
        textField.setOpaque(false); // Make the JTextField transparent
        textField.setBorder(null);
        
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "escapePressed");
        textField.getActionMap().put("escapePressed", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform the desired action when Escape is pressed
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(TexxtField.this);
                if (topFrame != null) {
                    topFrame.dispose(); // Close the frame
                }
            }
        });

        // Set layout to null for absolute positioning
        this.setLayout(null);

        // Add the JTextField to the JLabel
        this.add(textField);
    }

    public JTextField getTextField() {
        return textField;
    }
}
