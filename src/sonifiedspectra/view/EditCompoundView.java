package sonifiedspectra.view;

import sonifiedspectra.model.Compound;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hvandenberg on 5/30/15.
 */
public class EditCompoundView extends JPanel {

    private JTextField nameTextField;
    private JTextField typeTextField;
    private BetterButton selectFileButton;

    public EditCompoundView(Compound compound) {
        setLayout(null);
        setBounds(220, 45, 400, 47);
        setBackground(Color.decode("#F5F5F5"));
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        nameTextField = new JTextField();
        nameTextField.setText(compound.getName());
        nameTextField.setBounds(10, 5, 75, 20);
        add(nameTextField);

        typeTextField = new JTextField();
        typeTextField.setText(compound.getSpectrumType());
        typeTextField.setBounds(95, 5, 75, 20);
        add(typeTextField);

        selectFileButton = new BetterButton(Color.decode("#F5F5F5"), 100, 32, 5);
        selectFileButton.setBorderPainted(true);
        selectFileButton.setFocusPainted(false);
        selectFileButton.setBounds(180, 5, 100, 32);
        selectFileButton.setText(compound.getDataFile().getName() + ".ss");
        add(selectFileButton);

        setVisible(true);

        repaint();

    }
}
