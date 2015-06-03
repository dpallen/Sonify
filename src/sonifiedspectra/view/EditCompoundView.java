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
        setBounds(440, 45, 285, 40);
        setBackground(Color.decode("#F5F5F5"));
        setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        nameTextField = new JTextField();
        nameTextField.setText(compound.getName());
        nameTextField.setBounds(10, 10, 75, 20);
        add(nameTextField);

        typeTextField = new JTextField();
        typeTextField.setText(compound.getSpectrumType());
        typeTextField.setBounds(95, 10, 75, 20);
        add(typeTextField);

        selectFileButton = new BetterButton(Color.decode("#F5F5F5"), 100, 20, 5);
        selectFileButton.setBorderPainted(true);
        selectFileButton.setFocusPainted(false);
        selectFileButton.setBounds(175, 10, 100, 20);
        selectFileButton.setText(compound.getDataFile().getName() + ".ss");
        add(selectFileButton);

        setVisible(true);

        repaint();

    }

    public void updatePanel(Compound compound) {

        nameTextField.setText(compound.getName());
        typeTextField.setText(compound.getSpectrumType());
        selectFileButton.setText(compound.getDataFile().getName() + ".ss");

    }

}
