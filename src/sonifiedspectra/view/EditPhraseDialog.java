package sonifiedspectra.view;

import sonifiedspectra.model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EditPhraseDialog extends JDialog {
    private Sonify app;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTextField maxPitchTextField;
    private JTextField minPitchTextField;
    private JTextField x1TextField;
    private JTextField x2TextField;
    private JLabel compoundNameLabel;

    public EditPhraseDialog(Sonify app) throws IOException, FontFormatException {
        this.app = app;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setBackground(Color.decode("#F5F5F5"));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.decode("#979797"), 1, true));

        Font hnt20 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/HelveticaNeue-Thin.otf")).deriveFont(Font.PLAIN, 20);

        titleLabel.setFont(hnt20);

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

        app.getActivePhrase().setMinPitch(Integer.valueOf(minPitchTextField.getText()));
        app.getActivePhrase().setMaxPitch(Integer.valueOf(maxPitchTextField.getText()));
        app.getActivePhrase().setX1(Double.valueOf(x1TextField.getText()));
        app.getActivePhrase().setX2(Double.valueOf(x2TextField.getText()));

        ArrayList<Note> selectedNotes = app.getActivePhrase().getSelectedNotes();
        app.getActivePhrase().initialize();
        app.getActivePhrase().setSelectedNotes(selectedNotes);
        app.updateActivePhrase(app.getActivePhrase());
        app.updateIntervalMarker();

        setVisible(false);
        app.getFrame().pack();

    }

    private void onCancel() {

        setVisible(false);
    }

    public JTextField getMaxPitchTextField() {
        return maxPitchTextField;
    }

    public void setMaxPitchTextField(JTextField maxPitchTextField) {
        this.maxPitchTextField = maxPitchTextField;
    }

    public JTextField getMinPitchTextField() {
        return minPitchTextField;
    }

    public void setMinPitchTextField(JTextField minPitchTextField) {
        this.minPitchTextField = minPitchTextField;
    }

    public JLabel getCompoundNameLabel() {
        return compoundNameLabel;
    }

    public void setCompoundNameLabel(JLabel compoundNameLabel) {
        this.compoundNameLabel = compoundNameLabel;
    }

    public JTextField getX1TextField() {
        return x1TextField;
    }

    public void setX1TextField(JTextField x1TextField) {
        this.x1TextField = x1TextField;
    }

    public JTextField getX2TextField() {
        return x2TextField;
    }

    public void setX2TextField(JTextField x2TextField) {
        this.x2TextField = x2TextField;
    }
}
