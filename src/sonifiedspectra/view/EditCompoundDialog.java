package sonifiedspectra.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EditCompoundDialog extends JDialog {

    private Sonify app;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField typeTextField;
    private JLabel titleLabel;
    private JLabel dataLabel;

    public EditCompoundDialog(Sonify app) throws IOException, FontFormatException {
        this.app = app;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setBackground(Color.decode("#F5F5F5"));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/HelveticaNeue-Thin.otf"))).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);
        nameTextField.setText(app.getActivePhrase().getCompound().getName());
        typeTextField.setText(app.getActivePhrase().getCompound().getSpectrumType());
        dataLabel.setText(app.getActivePhrase().getCompound().getDataFile().getName() + ".ss");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        int index = app.getCompoundComboBox().getSelectedIndex();
        app.setTemp(false);
        app.getCompoundComboBox().removeItem(app.getActivePhrase().getCompound().getName());
        app.getActivePhrase().getCompound().setName(nameTextField.getText());
        app.getActivePhrase().getCompound().setSpectrumType(typeTextField.getText());
        app.getCompoundComboBox().insertItemAt(app.getActivePhrase().getCompound().getName(), index);
        app.getSpectrumLabel().setText(app.getActivePhrase().getCompound().getSpectrumType());
        app.getCompoundComboBox().setSelectedItem(app.getActivePhrase().getCompound().getName());
        app.setTemp(true);
        app.getActivePhrase().getCompound().getDataChart().getDataChart().getTitle().setText(
                app.getActivePhrase().getCompound().getName());
        app.getPhraseViewArray().get(app.getActiveProject().getPhrasesArray().indexOf(
                app.getActivePhrase())).getNameLabel().setText(app.getActivePhrase().getCompound().getName());

        for (TrackView tv : app.getTrackViewArray()) {
            for (PhraseInTrackView pitv : tv.getPhraseInTrackViewArray()) {
                if (pitv.getPhrase().getId() == app.getActivePhrase().getId()) pitv.getNameLabel().setText(
                        app.getActivePhrase().getCompound().getName());
            }
        }

        setVisible(false);
        app.getFrame().pack();
    }

    private void onCancel() {

        setVisible(false);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public JTextField getTypeTextField() {
        return typeTextField;
    }

    public void setTypeTextField(JTextField typeTextField) {
        this.typeTextField = typeTextField;
    }

    public JLabel getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(JLabel dataLabel) {
        this.dataLabel = dataLabel;
    }
}
